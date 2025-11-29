export interface LobbyDto {
    lobbyId: string;
    hostPlayerId: string;
    hostType: string;
    guestPlayerId?: string | null;
    guestType?: string | null;
}

export interface CreateLobbyRequest {
    playerId: string;
}
