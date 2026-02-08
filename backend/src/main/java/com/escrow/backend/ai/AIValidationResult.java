package com.escrow.backend.ai;

public class AIValidationResult {

    private double qualityScore;
    private double semanticScore;
    private boolean passed;
    private String summary;

    public AIValidationResult(
            double qualityScore,
            double semanticScore,
            boolean passed,
            String summary
    ) {
        this.qualityScore = qualityScore;
        this.semanticScore = semanticScore;
        this.passed = passed;
        this.summary = summary;
    }

    public double getQualityScore() { return qualityScore; }
    public double getSemanticScore() { return semanticScore; }
    public boolean isPassed() { return passed; }
    public String getSummary() { return summary; }
}
