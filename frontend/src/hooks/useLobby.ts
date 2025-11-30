import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
    createLobby,
    addPlayerToLobby,
    closeLobby,
    leaveLobby,
    startGame,
    chooseGameForLobby,
    getLobbyByPlayerId,
    getLobbyById,
    fetchOpenLobbies,
} from "../services/lobbyService";
import type { LobbyDto } from "../models/lobby";
import { useQuery as useQuery2 } from "@tanstack/react-query";
import { useContext } from "react";
import SecurityContext from "../context/SecurityContext";

// -----------------------------
// Create Lobby (server reads player from JWT)
// -----------------------------
export function useCreateLobby() {
    const queryClient = useQueryClient();

    return useMutation<LobbyDto, unknown, void>({
        mutationFn: () => createLobby(),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
        },
    });
}

// -----------------------------
// Add Player To Lobby (host adds another player by id)
// -----------------------------
export function useAddPlayerToLobby() {
    const queryClient = useQueryClient();

    return useMutation<LobbyDto, unknown, { lobbyId: string; playerId: string }>({
        mutationFn: ({ lobbyId, playerId }) => addPlayerToLobby(lobbyId, playerId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
            queryClient.invalidateQueries({ queryKey: ["open-lobbies"] });
        },
    });
}

// -----------------------------
// Close Lobby
// -----------------------------
export function useCloseLobby() {
    const queryClient = useQueryClient();

    return useMutation<void, unknown, string>({
        mutationFn: (lobbyId: string) => closeLobby(lobbyId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
            queryClient.invalidateQueries({ queryKey: ["open-lobbies"] });
        },
    });
}

// -----------------------------
// Leave Lobby
// -----------------------------
export function useLeaveLobby() {
    const queryClient = useQueryClient();

    return useMutation<void, unknown, { lobbyId: string; playerId: string }>({
        mutationFn: ({ lobbyId, playerId }) => leaveLobby(lobbyId, playerId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
            queryClient.invalidateQueries({ queryKey: ["open-lobbies"] });
        },
    });
}

// -----------------------------
// Start Game
// -----------------------------
export function useStartGame() {
    return useMutation<string | null, unknown, string>({
        mutationFn: (lobbyId: string) => startGame(lobbyId),
    });
}

// -----------------------------
// Choose Game
// -----------------------------
export function useChooseGame() {
    const queryClient = useQueryClient();

    return useMutation<void, unknown, { lobbyId: string; gameId: string }>({
        mutationFn: ({ lobbyId, gameId }) =>
            chooseGameForLobby(lobbyId, gameId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
        },
    });
}

// use by id
export function useLobbyById(lobbyId: string | null) {
    const { isAuthenticated } = useContext(SecurityContext);

    return useQuery2<LobbyDto>({
        queryKey: ["lobby-by-id", lobbyId],
        queryFn: () => getLobbyById(lobbyId!),
        enabled: !!lobbyId && isAuthenticated(),
        refetchInterval: 5000, // Reduced to 5 seconds for faster updates
    });
}

// low-level: fetches the lobby for the authenticated player (server reads JWT)
export function useLobbyByPlayerId() {
    const { isAuthenticated } = useContext(SecurityContext);

    return useQuery<LobbyDto | null>({
        queryKey: ["lobby-by-player"],
        queryFn: () => getLobbyByPlayerId(),
        enabled: isAuthenticated(), // Only run when authenticated
        refetchInterval: 5000, // Reduced to 5 seconds for faster updates
        retry: false, // Don't retry on 401 errors
    });
}

// Convenience wrapper matching example pattern (returns lobby + helpers)
export function useLobby() {
    const query = useLobbyByPlayerId();

    return {
        lobby: query.data ?? null,
        isLoading: query.isLoading,
        isError: !!query.error,
        refreshLobby: query.refetch,
    };
}

export function useOpenLobbies() {
    const { isAuthenticated } = useContext(SecurityContext);
    const queryClient = useQueryClient();

    // Use React Query instead of manual state management
    const query = useQuery<LobbyDto[]>({
        queryKey: ["open-lobbies"],
        queryFn: fetchOpenLobbies,
        enabled: isAuthenticated(), // Only run when authenticated
        refetchInterval: 3000, // Refresh every 3 seconds for real-time updates
        retry: false, // Don't retry on 401 errors
    });

    return {
        openLobbies: query.data ?? [],
        isLoadingLobbies: query.isLoading,
        isLobbiesError: !!query.error,
        refreshOpenLobbies: () => {
            queryClient.invalidateQueries({ queryKey: ["open-lobbies"] });
        },
    };
}