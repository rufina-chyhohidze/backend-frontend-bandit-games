package be.kdg.banditgames.platform.adapter.in.response;

public record StartGameResponse(
        String hostUrl,
        String guestUrl,
        String player1Type,
        String player2Type
) {
}
