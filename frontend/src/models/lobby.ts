export interface LobbyDto {
    lobbyId: string;
    hostPlayerId: string;
    hostType: string;
    guestPlayerId?: string | null;
    guestType?: string | null;
    gameId?: string | null;
    status?: "WAITING" | "IN_GAME";
}

