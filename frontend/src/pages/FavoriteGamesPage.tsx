import { useNavigate } from "react-router-dom";
import type { Game } from "../models/game";
import { GameList } from "../components/games/GameList";
import { useFavoriteGames, useToggleFavoriteGame } from "../hooks/useFavorites";

import {
    Box,
    CircularProgress,
    Typography,
    Alert,
    Button,
    Stack,
    Chip,
} from "@mui/material";
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";
import RefreshRoundedIcon from "@mui/icons-material/RefreshRounded";

export function FavoriteGamesPage() {
    const navigate = useNavigate();

    const {
        favoriteGames,
        isLoadingFavorites,
        isFavoritesError,
        refreshFavorites,
    } = useFavoriteGames();

    const toggleFavoriteMutation = useToggleFavoriteGame();

    const loading = isLoadingFavorites;
    const error = isFavoritesError
        ? "Failed to load favorite games. Please try again later."
        : null;

    const favoriteGameIds = favoriteGames.map((g) => g.gameId);
    const hasNoFavorites = !loading && !error && favoriteGames.length === 0;

    const handlePlay = (game: Game) => {
        const url = game.urlGameSession;
        if (url.startsWith("http")) {
            window.location.href = url;
        } else {
            navigate(url);
        }
    };

    const handleViewAchievements = (game: Game) => {
        navigate(`/games/${game.gameId}/achievements`);
    };

    const handleFavorite = (game: Game) => {
        toggleFavoriteMutation.mutate({ gameId: game.gameId, isFavorite: true });
    };

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
                        <SportsEsportsRoundedIcon sx={{ fontSize: 32, color: "#ffc94d" }} />
                        <Typography
                            variant="h4"
                            component="h1"
                            sx={{
                                fontWeight: 700,
                                letterSpacing: 0.5,
                                color: "#ffffff",
                            }}
                        >
                            My Favorite Games
                        </Typography>
                    </Stack>

                    <Typography
                        variant="body1"
                        sx={{
                            color: "#d0d0e5",
                            mt: 1,
                            maxWidth: 600,
                        }}
                    >
                        These are the games you starred as favorites. Click the star again to
                        remove them from this list.
                    </Typography>

                    <Stack direction="row" spacing={1} mt={2} flexWrap="wrap">
                        <Chip
                            label="Favorites"
                            size="small"
                            sx={{
                                bgcolor: "rgba(255, 201, 77, 0.18)",
                                color: "#ffe9ac",
                            }}
                        />
                        <Chip
                            label={`${favoriteGames.length} game${
                                favoriteGames.length === 1 ? "" : "s"
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
                        onClick={() => refreshFavorites()}
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
                                    "linear-gradient(120deg, rgba(255,201,77,0.25), rgba(0,220,130,0.25))",
                            },
                        }}
                    >
                        {loading ? "Loading..." : "Refresh"}
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
                                onClick={() => refreshFavorites()}
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

                    {hasNoFavorites && (
                        <Box sx={{ mt: 3 }}>
                            <Typography
                                variant="body1"
                                sx={{ color: "#cbd5f5", mb: 1 }}
                            >
                                You don’t have any favorite games yet.
                            </Typography>
                            <Button
                                variant="text"
                                onClick={() => navigate("/games")}
                                sx={{
                                    textTransform: "none",
                                    color: "#9d7dff",
                                    fontWeight: 500,
                                }}
                            >
                                Go to Games Library and star some games ⭐
                            </Button>
                        </Box>
                    )}

                    {!loading && !error && !hasNoFavorites && (
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
                                games={favoriteGames}
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

