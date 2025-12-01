package be.kdg.banditgames.platform.port.in.game;

public record GameSubmissionCommand(
        String name,
        String description,
        String rules,
        String pictureUrl,
        String urlGameSession
) {
}
