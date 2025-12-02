export interface FriendshipDto {
    playerA: string; // UUID
    playerB: string; // UUID
    status: FriendshipStatus;
    createdAt: string; // ISO string from LocalDateTime
    initiator: string; // <-- NEW FIELD
}

export interface PlayerDtoWithName {
    playerId: string; // UUID
    username: string;
}

export type FriendshipStatus = "PENDING" | "ACCEPTED";
