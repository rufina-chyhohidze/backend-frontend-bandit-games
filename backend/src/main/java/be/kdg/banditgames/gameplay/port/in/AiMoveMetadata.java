// port/in/AiMoveMetadata.java (or put in application/dto package)
package be.kdg.banditgames.gameplay.port.in;

public record AiMoveMetadata(
        String recommendedMove,
        double confidenceScore,
        String bestMove,
        Double heuristicScore,
        Integer visitCount,
        Integer searchDepth
) {
    // This can be null-safe if AI service doesn't always provide data
    public static AiMoveMetadata empty() {
        return new AiMoveMetadata(null, 0.0, null, null, null, null);
    }
}