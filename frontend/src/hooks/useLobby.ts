import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
    createLobby,
    addPlayerToLobby,
    closeLobby,
    leaveLobby,
    startGame,
    chooseGameForLobby
} from "../services/lobbyService.ts";
import type { CreateLobbyRequest } from "../models/lobby";


// -----------------------------
// Create Lobby
// -----------------------------
export function useCreateLobby() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: (req: CreateLobbyRequest) => createLobby(req),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
        }
    });
}


// -----------------------------
// Add Player To Lobby
// -----------------------------
export function useAddPlayerToLobby() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ lobbyId, playerId }: { lobbyId: string; playerId: string }) =>
            addPlayerToLobby(lobbyId, playerId),

        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
        }
    });
}


// -----------------------------
// Close Lobby
// -----------------------------
export function useCloseLobby() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: (lobbyId: string) => closeLobby(lobbyId),

        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
        }
    });
}


// -----------------------------
// Leave Lobby
// -----------------------------
export function useLeaveLobby() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ lobbyId, playerId }: { lobbyId: string; playerId: string }) =>
            leaveLobby(lobbyId, playerId),

        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
        }
    });
}


// -----------------------------
// Start Game (returns redirect URL)
// -----------------------------
export function useStartGame() {
    return useMutation({
        mutationFn: (lobbyId: string) => startGame(lobbyId),
    });
}


// -----------------------------
// Choose Game For Lobby
// -----------------------------
export function useChooseGame() {
    const queryClient = useQueryClient();

    return useMutation({
        mutationFn: ({ lobbyId, gameId }: { lobbyId: string; gameId: string }) =>
            chooseGameForLobby(lobbyId, gameId),

        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby"] });
        }
    });
}
