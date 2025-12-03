import { useContext } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import SecurityContext from "../context/SecurityContext";
import {
    addFavoriteGame,
    removeFavoriteGame,
    fetchCurrentPlayer,
    type PlayerDto,
} from "../services/playerService";
import type { Game } from "../models/game";
import {fetchFavoriteGames} from "../services/gamesService.ts";


export function useCurrentPlayer() {
    const { isAuthenticated } = useContext(SecurityContext);

    const query = useQuery<PlayerDto>({
        queryKey: ["player", "me"],
        queryFn: fetchCurrentPlayer,
        enabled: isAuthenticated(),
        retry: false,
    });

    return {
        player: query.data ?? null,
        favoriteGameIds: query.data?.favoriteGameIds ?? [],
        isLoadingPlayer: query.isLoading,
        isPlayerError: !!query.error,
        refreshPlayer: query.refetch,
    };
}

export function useFavoriteGames() {
    const { isAuthenticated } = useContext(SecurityContext);

    const query = useQuery<Game[]>({
        queryKey: ["player", "favorites"],
        queryFn: fetchFavoriteGames,
        enabled: isAuthenticated(),
        retry: false,
    });

    return {
        favoriteGames: query.data ?? [],
        isLoadingFavorites: query.isLoading,
        isFavoritesError: !!query.error,
        refreshFavorites: query.refetch,
    };
}

export function useToggleFavoriteGame() {
    const queryClient = useQueryClient();

    return useMutation<PlayerDto, unknown, { gameId: string; isFavorite: boolean }>({
        mutationFn: ({ gameId, isFavorite }) =>
            isFavorite ? removeFavoriteGame(gameId) : addFavoriteGame(gameId),
        onSuccess: (player) => {
            queryClient.setQueryData<PlayerDto>(["player", "me"], player);
            queryClient.invalidateQueries({ queryKey: ["player", "favorites"] });
        },
    });
}
