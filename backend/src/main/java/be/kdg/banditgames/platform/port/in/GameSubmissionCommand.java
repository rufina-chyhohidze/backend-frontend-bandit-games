package be.kdg.banditgames.platform.port.in;

public record GameSubmissionCommand(
        String name,
        String description,
        String rules,
        String pictureUrl,
        String urlGameSession
) {
}
