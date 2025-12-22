package be.kdg.banditgames.gameplay.adapter.out;

import org.springframework.data.mongodb.core.mapping.Field;


public class AiMetadataEmbedded {
    @Field("recommended_move")
    private String recommendedMove;
    
    @Field("confidence_score")
    private Double confidenceScore;
    
    @Field("best_move")
    private String bestMove;
    
    @Field("heuristic_score")
    private Double heuristicScore;
    
    @Field("visit_count")
    private Integer visitCount;
    
    @Field("search_depth")
    private Integer searchDepth;

    @Field("actual_move")
    private Integer actualMove;

    @Field("ai_hard_recommended_move")
    private Integer aiHardRecommendedMove;

    @Field("ai_hard_confidence")
    private Double aiHardConfidence;

    @Field("ai_hard_win_probability")
    private Double aiHardWinProbability;

    @Field("ai_ml_recommended_move")
    private Integer aiMlRecommendedMove;

    @Field("ai_ml_confidence")
    private Double aiMlConfidence;

    @Field("ai_ml_win_probability")
    private Double aiMlWinProbability;

    public AiMetadataEmbedded() {}

    public AiMetadataEmbedded(String recommendedMove, Double confidenceScore, String bestMove, Double heuristicScore, Integer visitCount, Integer searchDepth) {
        this.recommendedMove = recommendedMove;
        this.confidenceScore = confidenceScore;
        this.bestMove = bestMove;
        this.heuristicScore = heuristicScore;
        this.visitCount = visitCount;
        this.searchDepth = searchDepth;
    }

    public AiMetadataEmbedded(Integer actualMove,
                               Integer aiHardRecommendedMove, Double aiHardConfidence, Double aiHardWinProbability,
                               Integer aiMlRecommendedMove, Double aiMlConfidence, Double aiMlWinProbability) {
        this.actualMove = actualMove;
        this.aiHardRecommendedMove = aiHardRecommendedMove;
        this.aiHardConfidence = aiHardConfidence;
        this.aiHardWinProbability = aiHardWinProbability;
        this.aiMlRecommendedMove = aiMlRecommendedMove;
        this.aiMlConfidence = aiMlConfidence;
        this.aiMlWinProbability = aiMlWinProbability;
    }

    public String getRecommendedMove() {
        return recommendedMove;
    }

    public void setRecommendedMove(String recommendedMove) {
        this.recommendedMove = recommendedMove;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getBestMove() {
        return bestMove;
    }

    public void setBestMove(String bestMove) {
        this.bestMove = bestMove;
    }

    public Double getHeuristicScore() {
        return heuristicScore;
    }

    public void setHeuristicScore(Double heuristicScore) {
        this.heuristicScore = heuristicScore;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getSearchDepth() {
        return searchDepth;
    }

    public void setSearchDepth(Integer searchDepth) {
        this.searchDepth = searchDepth;
    }

    public Integer getActualMove() {
        return actualMove;
    }

    public void setActualMove(Integer actualMove) {
        this.actualMove = actualMove;
    }

    public Integer getAiHardRecommendedMove() {
        return aiHardRecommendedMove;
    }

    public void setAiHardRecommendedMove(Integer aiHardRecommendedMove) {
        this.aiHardRecommendedMove = aiHardRecommendedMove;
    }

    public Double getAiHardConfidence() {
        return aiHardConfidence;
    }

    public void setAiHardConfidence(Double aiHardConfidence) {
        this.aiHardConfidence = aiHardConfidence;
    }

    public Double getAiHardWinProbability() {
        return aiHardWinProbability;
    }

    public void setAiHardWinProbability(Double aiHardWinProbability) {
        this.aiHardWinProbability = aiHardWinProbability;
    }

    public Integer getAiMlRecommendedMove() {
        return aiMlRecommendedMove;
    }

    public void setAiMlRecommendedMove(Integer aiMlRecommendedMove) {
        this.aiMlRecommendedMove = aiMlRecommendedMove;
    }

    public Double getAiMlConfidence() {
        return aiMlConfidence;
    }

    public void setAiMlConfidence(Double aiMlConfidence) {
        this.aiMlConfidence = aiMlConfidence;
    }

    public Double getAiMlWinProbability() {
        return aiMlWinProbability;
    }

    public void setAiMlWinProbability(Double aiMlWinProbability) {
        this.aiMlWinProbability = aiMlWinProbability;
    }
}