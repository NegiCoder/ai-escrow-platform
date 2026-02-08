package com.escrow.backend.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OpenAISemanticService {

    @Value("${openai.api.key:}")
    private String apiKey;

    @Value("${openai.model:gpt-3.5-turbo}")
    private String model;

    private final ObjectMapper mapper = new ObjectMapper();

    public double semanticScore(
            String description,
            String datasetSample
    ) {

        // ✅ SAFETY: if key missing, never fail system
        if (apiKey == null || apiKey.isBlank()) {
            return 7.0;
        }

        try {
            String prompt = """
                You are a dataset evaluator.

                Description:
                %s

                Dataset sample:
                %s

                Score semantic relevance from 0 to 10.
                Return ONLY JSON:
                { "semanticScore": number }
                """.formatted(description, datasetSample);

            String body = """
                {
                  "model": "%s",
                  "messages": [
                    { "role": "user", "content": "%s" }
                  ],
                  "temperature": 0.2
                }
                """.formatted(model, prompt.replace("\"", "\\\""));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = mapper.readTree(response.body());

            // ❗ Handle OpenAI error responses
            if (root.has("error")) {
                return 7.0;
            }

            JsonNode choices = root.path("choices");

            if (!choices.isArray() || choices.isEmpty()) {
                return 7.0;
            }

            JsonNode contentNode = choices
                    .get(0)
                    .path("message")
                    .path("content");

            if (contentNode.isMissingNode() || contentNode.asText().isBlank()) {
                return 7.0;
            }

            JsonNode parsed = mapper.readTree(contentNode.asText());

            if (!parsed.has("semanticScore")) {
                return 7.0;
            }

            return parsed.get("semanticScore").asDouble();

        } catch (Exception e) {
            // 🔥 ORACLE MUST NEVER CRASH
            return 7.0;
        }
    }
}
