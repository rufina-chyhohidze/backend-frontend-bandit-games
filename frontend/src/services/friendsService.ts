import axios from "axios";
import type { FriendshipDto, PlayerDtoWithName } from "../models/friendship";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL;
const FRIENDSHIP_API_URL = `${BACKEND_URL}/api/friendships`;

// -----------------------------
// Send Friend Request
// -----------------------------
export const sendFriendRequest = async (
    fromPlayerId: string,
    toPlayerId: string
): Promise<FriendshipDto> => {
    const response = await axios.post(`${FRIENDSHIP_API_URL}/request`, {
        fromPlayerId,
        toPlayerId
    });
    return response.data;
};

// -----------------------------
// Accept Friend Request
// -----------------------------
export const acceptFriendRequest = async (
    fromPlayerId: string,
    toPlayerId: string
): Promise<void> => {
    await axios.post(`${FRIENDSHIP_API_URL}/accept`, {
        fromPlayerId,
        toPlayerId
    });
};

// -----------------------------
// Reject Friend Request
// -----------------------------
export const rejectFriendRequest = async (
    fromPlayerId: string,
    toPlayerId: string
): Promise<void> => {
    await axios.post(`${FRIENDSHIP_API_URL}/reject`, {
        fromPlayerId,
        toPlayerId
    });
};

// -----------------------------
// Remove Friend
// -----------------------------
export const removeFriend = async (
    playerAId: string,
    playerBId: string
): Promise<void> => {
    await axios.delete(`${FRIENDSHIP_API_URL}`, {
        data: { playerAId, playerBId }
    });
};

// -----------------------------
// Get Friends
// -----------------------------
export const getFriends = async (playerId: string): Promise<PlayerDtoWithName[]> => {
    const response = await axios.get<PlayerDtoWithName[]>(`${FRIENDSHIP_API_URL}/friends/${playerId}`);
    return response.data;
};

// -----------------------------
// Get Pending Friend Requests
// -----------------------------
export const getPendingRequests = async (playerId: string): Promise<PlayerDtoWithName[]> => {
    const response = await axios.get<PlayerDtoWithName[]>(`${FRIENDSHIP_API_URL}/pending/${playerId}`);
    return response.data;
};

// -----------------------------
// Get Friendship Between Two Players
// -----------------------------
export const getFriendshipBetween = async (
    playerAId: string,
    playerBId: string
): Promise<FriendshipDto | null> => {
    const response = await axios.get<FriendshipDto | null>(`${FRIENDSHIP_API_URL}/between`, {
        params: { playerAId, playerBId }
    });
    return response.data;
};
