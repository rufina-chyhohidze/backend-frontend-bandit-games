import type { Game } from "../models/Game";
import type { Achievement } from "../models/Achievement";

const API_BASE = "/api";

export async function fetchGames(): Promise<Game[]> {
    const response = await fetch(`${API_BASE}/games`);

    if (!response.ok) {
        throw new Error(`Failed to load games: ${response.status}`);
    }

    return response.json();
}

export async function fetchAchievements(gameId: string): Promise<Achievement[]> {
    const response = await fetch(`${API_BASE}/games/${gameId}/achievements`);

    if (!response.ok) {
        throw new Error(`Failed to load achievements: ${response.status}`);
    }

    return response.json();
}
