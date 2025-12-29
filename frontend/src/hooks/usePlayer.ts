// hooks/usePlayer.ts
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
    getCurrentPlayer,
    registerPlayer,
    searchPlayersByUsername,
    getPlayerById,
} from "../services/playerService";
import type { PlayerDto } from "../models/player";
import { useContext } from "react";
import SecurityContext from "../context/SecurityContext";
import type {PlayerDtoWithName} from "../models/friendship.ts";

// -----------------------------
// Register / Ensure Player Exists
// -----------------------------
export function useRegisterPlayer() {
    const queryClient = useQueryClient();

    return useMutation<PlayerDto, unknown, void>({
        mutationFn: () => registerPlayer(),
        onSuccess: (player) => {
            queryClient.setQueryData(["current-player"], player);
        },
    });
}

// -----------------------------
// Get Current Player Info
// -----------------------------
export function useCurrentPlayer() {
    const { isAuthenticated } = useContext(SecurityContext);

    return useQuery<PlayerDto>({
        queryKey: ["current-player"],
        queryFn: () => getCurrentPlayer(),
        enabled: isAuthenticated(),
        refetchInterval: 60000, // Refresh every 60s
        retry: false,
    });
}

// -----------------------------
// Search Players by Username
// -----------------------------
export function useSearchPlayers(username: string) {
    return useQuery<PlayerDtoWithName[]>({
        queryKey: ["search-players", username],
        queryFn: async () => {
            const players = await searchPlayersByUsername(username);
            return players.map(p => ({ playerId: p.id, username: p.username }));
        },
        enabled: !!username,
    });
}
// -----------------------------
// Get Player by ID
// -----------------------------
export function usePlayerById(playerId: string | null) {
    const { isAuthenticated } = useContext(SecurityContext);

    return useQuery<PlayerDto>({
        queryKey: ["player-by-id", playerId],
        queryFn: () => getPlayerById(playerId!),
        enabled: !!playerId && isAuthenticated(),
        retry: false,
    });
}
