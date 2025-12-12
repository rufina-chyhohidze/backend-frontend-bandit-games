import axios from "axios";
import type { UnlockedAchievement } from "../models/unlockedAchievement";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;



export async function fetchMyUnlockedAchievements(gameId: string): Promise<UnlockedAchievement[]> {
    const res = await axios.get(`${BACKEND_URL}/api/player/me/achievements`, {
        params: { gameId },
    });
    return res.data;
}

export async function fetchFriendUnlockedAchievements(friendId: string, gameId: string): Promise<UnlockedAchievement[]> {
    const res = await axios.get(`${BACKEND_URL}/api/player/${friendId}/achievements`, {
        params: { gameId },
    });
    return res.data;
}
