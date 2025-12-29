package be.kdg.banditgames.gameplay.adapter.in.response;

public record MLRecommendedMoveDto (
        String recommendedMove,
        double winProbability
){
}
