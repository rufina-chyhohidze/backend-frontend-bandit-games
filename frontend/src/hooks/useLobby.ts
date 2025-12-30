import {useMutation, useQuery, useQuery as useQuery2, useQueryClient} from "@tanstack/react-query";
import {
    addPlayerToLobby,
    chooseGameForLobby,
    closeLobby,
    createLobby,
    fetchOpenLobbies,
    getLobbyById,
    getLobbyByPlayerId,
    leaveLobby,
    startGame,
    chooseAiOpponent
} from "../services/lobbyService";
import type {LobbyDto} from "../models/lobby";
import {useContext} from "react";
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
type StartGameResponse = {
    hostUrl: string;
    guestUrl: string;
    player1Type: string;
    player2Type: string;
};

export function useStartGame(
    loggedInUser: { id?: string; name?: string } | undefined,
    lobby: { hostPlayerId: string } | undefined
) {
    return useMutation<StartGameResponse, unknown, string>({
        mutationFn: (lobbyId: string) => startGame(lobbyId),

        onSuccess: (data) => {
            if (!data) {
                console.error("Start Game returned no data.");
                return;
            }

            if (!loggedInUser || !lobby) {
                console.error("Cannot redirect: missing loggedInUser or lobby.");
                return;
            }

            const { hostUrl, guestUrl } = data;

            const currentUserId = loggedInUser.id ?? loggedInUser.name;

            window.location.href = currentUserId === lobby.hostPlayerId
                ? hostUrl
                : guestUrl;
        },

        onError: (error) => {
            console.error("Failed to start game:", error);
        }
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
            queryClient.invalidateQueries({ queryKey: ["lobby-by-player"] });
            queryClient.invalidateQueries({ queryKey: ["open-lobbies"] });
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
        refetchInterval: 1000, // Reduced to 5 seconds for faster updates
    });
}

export function useLobbyByPlayerId() {
    const { isAuthenticated } = useContext(SecurityContext);

    return useQuery<LobbyDto | null>({
        queryKey: ["lobby-by-player"],
        queryFn: () => getLobbyByPlayerId(),
        enabled: isAuthenticated(), // Only run when authenticated
        refetchInterval: 1000, // Reduced to 5 seconds for faster updates
        retry: false, // Don't retry on 401 errors
    });
}

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
        enabled: isAuthenticated(),
        refetchInterval: 3000,
        retry: false,
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

export function useChooseAiOpponent() {
    const queryClient = useQueryClient();

    return useMutation<void, unknown, { lobbyId: string; difficulty: "EASY" | "MEDIUM" | "HARD" | "ML" }>({
        mutationFn: ({ lobbyId, difficulty }) => chooseAiOpponent(lobbyId, difficulty),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["lobby-by-player"] });
            queryClient.invalidateQueries({ queryKey: ["open-lobbies"] });
        },
    });
}
