import axios from "axios";
import type { Game } from "../models/game.ts";
import type { Achievement } from "../models/achievement.ts";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;
const GAME_API_URL = `${BACKEND_URL}/api/games`;
const PLAYER_API_URL = `${BACKEND_URL}/api/player`;

// -----------------------------
// Fetch all games
// -----------------------------
export async function fetchGames(): Promise<Game[]> {
    const response = await axios.get(GAME_API_URL);
    return response.data;
}

// -----------------------------
// Fetch achievements for a game
// -----------------------------
export async function fetchAchievements(gameId: string): Promise<Achievement[]> {
    const response = await axios.get(`${GAME_API_URL}/${gameId}/achievements`);
    return response.data;
}
export async function fetchFavoriteGames(): Promise<Game[]> {
    const response = await axios.get(`${PLAYER_API_URL}/favorites`);
    return response.data;
}