import axios from "axios";
import type { LobbyDto } from "../models/lobby";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL;
const LOBBY_API_URL = `${BACKEND_URL}/api/lobby`;

// -----------------------------
// Create Lobby (server determines player from JWT)
// -----------------------------
export const createLobby = async (): Promise<LobbyDto> => {
    const response = await axios.post(`${LOBBY_API_URL}/create`);
    return response.data;
};

// -----------------------------
// Add Player To Lobby (host adds another player by id)
// -----------------------------
export const addPlayerToLobby = async (
    lobbyId: string,
    playerId: string
): Promise<LobbyDto> => {
    const response = await axios.post(`${LOBBY_API_URL}/${lobbyId}/add-player`, null, {
        params: { playerId },
    });
    return response.data;
};

// -----------------------------
// Close Lobby
// -----------------------------
export const closeLobby = async (lobbyId: string): Promise<void> => {
    await axios.delete(`${LOBBY_API_URL}/${lobbyId}`);
};

// -----------------------------
// Leave Lobby
// -----------------------------
export const leaveLobby = async (lobbyId: string, playerId: string): Promise<void> => {
    await axios.post(`${LOBBY_API_URL}/${lobbyId}/leave-lobby`, null, { params: { playerId } });
};

// -----------------------------
// Start Game
// -----------------------------
export const startGame = async (lobbyId: string) => {
    try {
        const response = await axios.post(
            `${LOBBY_API_URL}/${lobbyId}/start-game`,
        );

        return response.data; // JSON: hostUrl, guestUrl, player1Type, player2Type
    } catch (err) {
        console.error("Failed to start game:", err);
        return null;
    }
};

// -----------------------------
// Choose Game
// -----------------------------
export const chooseGameForLobby = async (
    lobbyId: string,
    gameId: string
): Promise<void> => {
    await axios.post(`${LOBBY_API_URL}/${lobbyId}/choose-game`, null, {
        params: { gameId }
    });
};

// -----------------------------
// Get Lobby by LobbyId
// -----------------------------
export const getLobbyById = async (lobbyId: string): Promise<LobbyDto> => {
    const response = await axios.get(`${LOBBY_API_URL}/by-id`, {
        params: { lobbyId },
    });

    return response.data;
};

// -----------------------------
// Get Lobby by Player (from JWT, no param needed)
// -----------------------------
export const getLobbyByPlayerId = async (): Promise<LobbyDto | null> => {
    try {
        const response = await axios.get(`${LOBBY_API_URL}/by-player`);
        return response.data || null; // handle null if lobby doesn't exist
    } catch (err: any) {
        if (err.response?.status === 404) return null; // optional, in case your backend returns 404
        throw err;
    }
};

///----------------------------
// Fetch Open Lobbies List
//-----------------------------

export async function fetchOpenLobbies(): Promise<LobbyDto[]> {
    try {
        const response = await axios.get<LobbyDto[]>(`${LOBBY_API_URL}/all`);
        return response.data || [];
    } catch (error) {
        console.error("Error fetching open lobbies:", error);
        throw new Error("Failed to load open lobbies.");
    }
}