package com.escrow.backend.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIValidationService {

    private final DatasetQualityService qualityService;
    private final OpenAISemanticService semanticService;

    public AIValidationResult validate(
            String description,
            List<Map<String, String>> datasetRows
    ) throws Exception {

        double quality = qualityService.calculateQualityScore(datasetRows);
        double semantic = semanticService.semanticScore(
                description,
                datasetRows.subList(0, Math.min(10, datasetRows.size())).toString()
        );

        boolean passed = quality >= 6.5 && semantic >= 7.0;

        return new AIValidationResult(
                quality,
                semantic,
                passed,
                passed
                    ? "Dataset accepted by AI oracle"
                    : "Dataset rejected due to low quality or relevance"
        );
    }
}
