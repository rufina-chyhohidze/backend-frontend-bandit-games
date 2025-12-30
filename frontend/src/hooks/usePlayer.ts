import {  useQuery} from "@tanstack/react-query";
import {
    searchPlayersByUsername,
    getPlayerById,
} from "../services/playerService";
import type { PlayerDto } from "../models/player";
import { useContext } from "react";
import SecurityContext from "../context/SecurityContext";
import type {PlayerDtoWithName} from "../models/friendship.ts";

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
