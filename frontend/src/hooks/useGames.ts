import { useQuery } from "@tanstack/react-query";
import type { Game } from "../models/game";
import { fetchGames } from "../services/gamesService";
import { useContext } from "react";
import SecurityContext from "../context/SecurityContext";

export function useGames() {
    const { isAuthenticated } = useContext(SecurityContext);

    const query = useQuery<Game[]>({
        queryKey: ["games"],
        queryFn: fetchGames,
        enabled: isAuthenticated(),
        retry: false,
    });

    return {
        games: query.data ?? [],
        isLoadingGames: query.isLoading,
        isGamesError: !!query.error,
        refreshGames: query.refetch,
    };
}
