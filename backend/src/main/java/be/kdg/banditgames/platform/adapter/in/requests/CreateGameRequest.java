package be.kdg.banditgames.platform.adapter.in.requests;

public record CreateGameRequest(
    String name,
    String description,
    String rules,
    String pictureUrl,
    String urlGameSession
) {
}
