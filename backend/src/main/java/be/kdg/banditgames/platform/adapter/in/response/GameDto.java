package be.kdg.banditgames.platform.adapter.in.response;

public record GameDto(
        String name,
        String description,
        String rules,
        String pictureUrl,
        String urlGameSession
) {
}
