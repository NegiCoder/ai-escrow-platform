package com.escrow.backend.ai;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatasetQualityService {

    public double calculateQualityScore(
            List<Map<String, String>> rows
    ) {
        if (rows == null || rows.isEmpty()) return 0;

        int rowCount = rows.size();
        int columnCount = rows.get(0).size();

        long missingCells = rows.stream()
                .flatMap(r -> r.values().stream())
                .filter(v -> v == null || v.isBlank())
                .count();

        long totalCells = (long) rowCount * columnCount;
        double missingRatio = (double) missingCells / totalCells;

        double score = 10.0;

        if (rowCount < 50) score -= 2;
        if (columnCount < 2) score -= 2;
        if (missingRatio > 0.2) score -= 3;

        return Math.max(score, 0);
    }
}
