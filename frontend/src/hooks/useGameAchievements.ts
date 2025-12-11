import { useEffect, useState } from "react";
import type { Achievement } from "../models/achievement";
import axios from "axios";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL;
const GAME_API_URL = `${BACKEND_URL}/api/games`;

interface UseGameAchievementsResult {
    achievements: Achievement[];
    isLoading: boolean;
    isError: boolean;
    error: string | null;
}

export function useGameAchievements(gameId?: string): UseGameAchievementsResult {
    const [achievements, setAchievements] = useState<Achievement[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!gameId) return;

        let cancelled = false;

        async function load() {
            try {
                setIsLoading(true);
                setError(null);
                const res = await axios.get<Achievement[]>(`${GAME_API_URL}/${gameId}/achievements`);
                if (!cancelled) {
                    setAchievements(res.data);
                }
            } catch (e: any) {
                if (!cancelled) {
                    setError(e.message ?? "Failed to load achievements");
                }
            } finally {
                if (!cancelled) {
                    setIsLoading(false);
                }
            }
        }

        load();

        return () => {
            cancelled = true;
        };
    }, [gameId]);

    return {
        achievements,
        isLoading,
        isError: !!error,
        error,
    };
}
