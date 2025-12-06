import axios from "axios";
import type { PlayerDto } from "../models/player";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL;
const PLAYER_API_URL = `${BACKEND_URL}/api/player`;

// -----------------------------
// Register / Ensure Player Exists
// -----------------------------
export const registerPlayer = async (): Promise<PlayerDto> => {
    const response = await axios.post(`${PLAYER_API_URL}/register`);
    return response.data;
};

// -----------------------------
// Get Current Player Info
// -----------------------------
export const getCurrentPlayer = async (): Promise<PlayerDto> => {
    const response = await axios.get(`${PLAYER_API_URL}/me`);
    return response.data;
};

// -----------------------------
// Search Players by Username
// -----------------------------
export const searchPlayersByUsername = async (username: string): Promise<PlayerDto[]> => {
    const response = await axios.get(`${PLAYER_API_URL}/search`, {
        params: { username },
    });
    return response.data;
};

// -----------------------------
// Get Player by ID
// -----------------------------
export const getPlayerById = async (playerId: string): Promise<PlayerDto> => {
    const response = await axios.get(`${PLAYER_API_URL}/by-id`, {
        params: { playerId },
    });
    return response.data;
};
