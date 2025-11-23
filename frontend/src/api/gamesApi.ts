import type { Game } from "../models/Game";

export async function fetchPlayableGames(): Promise<Game[]> {
    const response = await fetch("/api/games", {
        headers: {
            Accept: "application/json",
        },
        credentials: "include", // future-proof for auth/cookies
    });

    if (!response.ok) {
        throw new Error(`Failed to load games: ${response.status}`);
    }

    return response.json();
}
