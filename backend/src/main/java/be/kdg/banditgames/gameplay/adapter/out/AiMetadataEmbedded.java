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

    public AiMetadataEmbedded() {}
    public AiMetadataEmbedded(String recommendedMove, Double confidenceScore, String bestMove, Double heuristicScore, Integer visitCount, Integer searchDepth) {
        this.recommendedMove = recommendedMove;
        this.confidenceScore = confidenceScore;
        this.bestMove = bestMove;
        this.heuristicScore = heuristicScore;
        this.visitCount = visitCount;
        this.searchDepth = searchDepth;
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
}