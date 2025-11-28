import axios from "axios";
import type { LobbyDto, CreateLobbyRequest } from "../models/lobby";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL;

const LOBBY_API_URL = `${BACKEND_URL}/api/lobby`;


// -----------------------------
// Create Lobby
// -----------------------------
export const createLobby = async (request: CreateLobbyRequest): Promise<LobbyDto> => {
    const response = await axios.post(`${LOBBY_API_URL}/create`, request);
    return response.data;
};


// -----------------------------
// Add Player To Lobby
// -----------------------------
export const addPlayerToLobby = async (
    lobbyId: string,
    playerId: string
): Promise<LobbyDto> => {
    const response = await axios.post(
        `${LOBBY_API_URL}/${lobbyId}/add-player`,
        null,
        {
            params: { playerId }
        }
    );
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
export const leaveLobby = async (
    lobbyId: string,
    playerId: string
): Promise<void> => {
    await axios.post(
        `${LOBBY_API_URL}/${lobbyId}/leave-lobby`,
        null,
        {
            params: { playerId }
        }
    );
};


// -----------------------------
// Start Game (303 redirect URL is returned in "Location")
// -----------------------------
export const startGame = async (lobbyId: string): Promise<string | null> => {
    try {
        const response = await axios.post(`${LOBBY_API_URL}/${lobbyId}/start-game`, null, {
            maxRedirects: 0,
            validateStatus: status => status === 303
        });

        return response.headers["location"] ?? null;
    } catch (err) {
        console.error("Failed to start game:", err);
        return null;
    }
};


// -----------------------------
// Choose Game For Lobby
// -----------------------------
export const chooseGameForLobby = async (
    lobbyId: string,
    gameId: string
): Promise<void> => {
    await axios.post(
        `${LOBBY_API_URL}/${lobbyId}/choose-game`,
        null,
        {
            params: { gameId }
        }
    );
};
