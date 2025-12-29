import { useState, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import type { Game } from "../models/game";
import { GameList } from "../components/games/GameList";
import { useGames } from "../hooks/useGames";
import { useCurrentPlayer, useToggleFavoriteGame } from "../hooks/useFavorites";

import {
    Box,
    CircularProgress,
    Typography,
    Alert,
    Button,
    Stack,
    Chip,
    InputBase,
} from "@mui/material";
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";
import RefreshRoundedIcon from "@mui/icons-material/RefreshRounded";
import SearchIcon from "@mui/icons-material/Search";

export function GamesPage() {
    const navigate = useNavigate();

    const { games, isLoadingGames, isGamesError, refreshGames } = useGames();
    const { favoriteGameIds, isLoadingPlayer } = useCurrentPlayer();
    const toggleFavoriteMutation = useToggleFavoriteGame();

    const [search, setSearch] = useState("");

    const loading = isLoadingGames || isLoadingPlayer;
    const error = isGamesError ? "Failed to load games. Please try again later." : null;

    const handlePlay = (game: Game) => {
        navigate("/lobby");
    };

    const handleViewAchievements = (game: Game) => {
        navigate(`/games/${game.gameId}/achievements`);
    };

    const handleFavorite = (game: Game) => {
        const isFavorite = favoriteGameIds.includes(game.gameId);
        toggleFavoriteMutation.mutate({ gameId: game.gameId, isFavorite });
    };

    const handleSearchChange = (value: string) => {
        setSearch(value);
    };

    const filteredGames = useMemo(
        () =>
            games.filter((g) =>
                g.name.toLowerCase().includes(search.toLowerCase())
            ),
        [games, search]
    );

    return (
        <Box
            sx={{
                width: "100%",
                minHeight: "100vh",
                display: "flex",
                justifyContent: "center",
                alignItems: "flex-start",
                background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                pt: { xs: 9, sm: 10 },
                pb: 4,
            }}
        >
            <Box
                sx={{
                    width: { xs: "98vw", sm: 700, md: 900 },
                    maxWidth: "98vw",
                    borderRadius: 4,
                    p: { xs: 2, md: 4 },
                    pt: { xs: 6, md: 7 },
                    bgcolor: "rgba(10, 16, 36, 0.97)",
                    boxShadow: "0 24px 60px rgba(0,0,0,0.7)",
                    position: "relative",
                }}
            >
                <Box
                    sx={{
                        position: "absolute",
                        inset: 0,
                        pointerEvents: "none",
                        opacity: 0.4,
                        background:
                            "radial-gradient(circle at 0% 0%, rgba(0,200,255,0.18), transparent 55%)," +
                            "radial-gradient(circle at 100% 100%, rgba(255,0,128,0.23), transparent 55%)",
                        zIndex: 0,
                        borderRadius: 4,
                    }}
                />

                <Stack
                    spacing={3}
                    alignItems="center"
                    sx={{
                        textAlign: "center",
                        position: "relative",
                        zIndex: 1,
                    }}
                >
                    <Stack direction="row" alignItems="center" spacing={1}>
                        <SportsEsportsRoundedIcon sx={{ fontSize: 32, color: "#9d7dff" }} />
                        <Typography
                            variant="h4"
                            component="h1"
                            sx={{
                                fontWeight: 700,
                                letterSpacing: 0.5,
                                color: "#ffffff",
                            }}
                        >
                            Games Library
                        </Typography>
                    </Stack>

                    <Typography
                        variant="body1"
                        sx={{ color: "#d0d0e5", mt: 1, maxWidth: 600 }}
                    >
                        Pick a game to start playing, explore achievements, or star your favorites.
                    </Typography>

                    <Box
                        sx={{
                            mt: 1,
                            display: "flex",
                            alignItems: "center",
                            bgcolor: "rgba(15,23,42,0.95)",
                            borderRadius: 999,
                            px: 2,
                            py: 0.5,
                            border: "1px solid rgba(148,163,184,0.5)",
                            width: { xs: "100%", sm: "70%" },
                            maxWidth: 600,
                            gap: 1,
                        }}
                    >
                        <SearchIcon sx={{ fontSize: 18, color: "#9ca3af" }} />
                        <InputBase
                            placeholder="Search games..."
                            sx={{ fontSize: 14, flexGrow: 1, color: "#e5e7eb" }}
                            value={search}
                            onChange={(e) => handleSearchChange(e.target.value)}
                        />
                    </Box>

                    <Stack direction="row" spacing={1} mt={1} flexWrap="wrap">
                        <Chip
                            label="All platforms"
                            size="small"
                            sx={{
                                bgcolor: "rgba(157, 125, 255, 0.12)",
                                color: "#e3ddff",
                            }}
                        />
                        <Chip
                            label={`${filteredGames.length} game${
                                filteredGames.length === 1 ? "" : "s"
                            }`}
                            size="small"
                            sx={{
                                bgcolor: "rgba(0, 220, 130, 0.12)",
                                color: "#c8ffe9",
                            }}
                        />
                    </Stack>

                    <Button
                        variant="outlined"
                        onClick={() => refreshGames()}
                        disabled={loading}
                        startIcon={<RefreshRoundedIcon />}
                        sx={{
                            alignSelf: "center",
                            borderColor: "rgba(255,255,255,0.6)",
                            color: "#ffffff",
                            textTransform: "none",
                            fontWeight: 500,
                            px: 3,
                            py: 1,
                            borderRadius: 100,
                            "&:hover": {
                                borderColor: "#ffffff",
                                background:
                                    "linear-gradient(120deg, rgba(157,125,255,0.25), rgba(0,220,130,0.25))",
                            },
                        }}
                    >
                        {loading ? "Loading..." : "Refresh list"}
                    </Button>

                    {loading && (
                        <Box sx={{ display: "flex", justifyContent: "center", py: 5 }}>
                            <CircularProgress sx={{ color: "#ffffff" }} />
                        </Box>
                    )}

                    {!loading && error && (
                        <Stack spacing={2}>
                            <Alert severity="error" variant="filled">
                                {error}
                            </Alert>
                            <Button
                                variant="contained"
                                onClick={() => refreshGames()}
                                startIcon={<RefreshRoundedIcon />}
                                sx={{
                                    alignSelf: "center",
                                    textTransform: "none",
                                    borderRadius: 100,
                                    background: "linear-gradient(120deg, #ff5f6d, #ffc371)",
                                }}
                            >
                                Retry
                            </Button>
                        </Stack>
                    )}

                    {!loading && !error && (
                        <Box
                            sx={{
                                mt: 1,
                                borderRadius: 3,
                                border: "1px solid rgba(255,255,255,0.06)",
                                bgcolor: "rgba(7,12,30,0.85)",
                                p: { xs: 2, md: 3 },
                                boxShadow: "0 18px 40px rgba(0,0,0,0.5)",
                                display: "flex",
                                flexDirection: { xs: "column", md: "row" },
                                gap: 2,
                                justifyContent: "center",
                                alignItems: "center",
                            }}
                        >
                            <GameList
                                games={filteredGames}
                                onPlay={handlePlay}
                                onViewAchievements={handleViewAchievements}
                                onFavorite={handleFavorite}
                                favoriteGameIds={favoriteGameIds}
                            />
                        </Box>
                    )}
                </Stack>
            </Box>
        </Box>
    );
}
