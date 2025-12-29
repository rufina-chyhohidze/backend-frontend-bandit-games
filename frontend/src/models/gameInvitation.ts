export type InvitationStatus = "PENDING" | "ACCEPTED" | "REJECTED" | "CANCELED" | "EXPIRED";

export interface GameInvitationDto {
    id: { id: string } | string;
    fromPlayer: string;
    toPlayer: string;
    lobbyId: { lobbyID: string } | string;
    status: InvitationStatus;
    createdAt: string;
}
