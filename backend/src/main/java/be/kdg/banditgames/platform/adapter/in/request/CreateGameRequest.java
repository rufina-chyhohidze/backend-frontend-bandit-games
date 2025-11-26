package be.kdg.banditgames.platform.adapter.in.request;

public record CreateGameRequest(
    String name,
    String description,
    String rules,
    String pictureUrl,
    String urlGameSession
) {
}
