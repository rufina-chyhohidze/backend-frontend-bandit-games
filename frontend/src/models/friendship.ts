export interface FriendshipDto {
    playerA: string;
    playerB: string;
    status: FriendshipStatus;
    createdAt: string;
    initiator: string;
}

export interface PlayerDtoWithName {
    playerId: string;
    username: string;
}

export type FriendshipStatus = "PENDING" | "ACCEPTED";
