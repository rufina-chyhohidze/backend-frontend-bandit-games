import { useQuery } from "@tanstack/react-query";
import {
    fetchMyUnlockedAchievements,
    fetchFriendUnlockedAchievements,
} from "../services/achievementService";

export function useMyUnlockedAchievements(gameId: string | undefined) {
    return useQuery({
        queryKey: ["achievements", "unlocked", "me", gameId],
        enabled: !!gameId,
        queryFn: () => fetchMyUnlockedAchievements(gameId!),
    });
}

export function useFriendUnlockedAchievements(friendId: string | undefined, gameId: string | undefined) {
    return useQuery({
        queryKey: ["achievements", "unlocked", "friend", friendId, gameId],
        enabled: !!friendId && !!gameId,
        queryFn: () => fetchFriendUnlockedAchievements(friendId!, gameId!),
    });
}
