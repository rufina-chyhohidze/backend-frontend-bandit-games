import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import type { Game } from "../models/Game";
import { fetchGames } from "../api/gamesApi";
import { GameList } from "../components/games/GameList";
import {
    Box,
    CircularProgress,
    Container,
    Typography,
    Alert,
    Button,
    Stack,
    Chip,
} from "@mui/material";
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";
import RefreshRoundedIcon from "@mui/icons-material/RefreshRounded";

export function GamesPage() {
    const [games, setGames] = useState<Game[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const loadGames = async () => {
        try {
            setLoading(true);
            const result = await fetchGames();
            setGames(result);
            setError(null);
        } catch (err) {
            console.error(err);
            setError("Failed to load games. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadGames();
    }, []);

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

    return (
        <Container
            maxWidth="lg"
            sx={{
                py: 6,
                position: "relative",
            }}
        >
            {/* Background glow */}
            <Box
                sx={{
                    position: "absolute",
                    inset: 0,
                    pointerEvents: "none",
                    opacity: 0.7,
                    background:
                        "radial-gradient(circle at 0% 0%, rgba(0, 200, 255, 0.18), transparent 55%), radial-gradient(circle at 100% 100%, rgba(255, 0, 128, 0.23), transparent 55%)",
                    zIndex: 0,
                }}
            />

            <Box
                sx={{
                    position: "relative",
                    zIndex: 1,
                    borderRadius: 4,
                    p: { xs: 3, md: 4 },
                    bgcolor: "rgba(5, 10, 25, 0.92)",
                    border: "1px solid rgba(255,255,255,0.06)",
                    boxShadow: "0 24px 60px rgba(0,0,0,0.8)",
                    backdropFilter: "blur(18px)",
                }}
            >
                <Stack spacing={3} alignItems="center" sx={{ textAlign: "center" }}>

                {/* Header */}
                    <Stack
                        direction={{ xs: "column", md: "row" }}
                        justifyContent="space-between"
                        alignItems={{ xs: "flex-start", md: "center" }}
                        gap={2}
                    >
                        <Box>
                            <Stack direction="row" alignItems="center" spacing={1}>
                                <SportsEsportsRoundedIcon
                                    sx={{ fontSize: 32, color: "#9d7dff" }}
                                />
                                <Typography
                                    variant="h4"
                                    component="h1"
                                    sx={{
                                        fontWeight: 700,
                                        letterSpacing: 0.5,
                                        color: "#ffffff",
                                    }}
                                >
                                    Available Games
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
                                Choose a game and dive in. External games open in a new
                                page, internal games keep you inside BanditGames.
                            </Typography>

                            <Stack direction="row" spacing={1} mt={2} flexWrap="wrap">
                                <Chip
                                    label="Internal & external experiences"
                                    size="small"
                                    sx={{
                                        bgcolor: "rgba(157, 125, 255, 0.12)",
                                        color: "#e3ddff",
                                        border: "1px solid rgba(157, 125, 255, 0.6)",
                                    }}
                                />
                                <Chip
                                    label={`${games.length} game${games.length === 1 ? "" : "s"
                                    } available`}
                                    size="small"
                                    sx={{
                                        bgcolor: "rgba(0, 220, 130, 0.12)",
                                        color: "#c8ffe9",
                                        border: "1px solid rgba(0, 220, 130, 0.6)",
                                    }}
                                />
                            </Stack>
                        </Box>

                        <Button
                            variant="outlined"
                            onClick={loadGames}
                            disabled={loading}
                            startIcon={<RefreshRoundedIcon />}
                            sx={{
                                alignSelf: { xs: "stretch", md: "center" },
                                borderColor: "rgba(255,255,255,0.6)",
                                color: "#ffffff",
                                textTransform: "none",
                                fontWeight: 500,
                                px: 2.5,
                                py: 1,
                                borderRadius: 999,
                                "&:hover": {
                                    borderColor: "#ffffff",
                                    background:
                                        "linear-gradient(120deg, rgba(157,125,255,0.25), rgba(0,220,130,0.25))",
                                },
                            }}
                        >
                            {loading ? "Loading..." : "Refresh list"}
                        </Button>
                    </Stack>

                    {/* Loading */}
                    {loading && (
                        <Box
                            sx={{
                                display: "flex",
                                justifyContent: "center",
                                py: 5,
                            }}
                        >
                            <CircularProgress sx={{ color: "#ffffff" }} />
                        </Box>
                    )}

                    {/* Error */}
                    {!loading && error && (
                        <Stack spacing={2}>
                            <Alert severity="error" variant="filled">
                                {error}
                            </Alert>
                            <Button
                                variant="contained"
                                onClick={loadGames}
                                startIcon={<RefreshRoundedIcon />}
                                sx={{
                                    alignSelf: "flex-start",
                                    textTransform: "none",
                                    borderRadius: 999,
                                    background:
                                        "linear-gradient(120deg, #ff5f6d, #ffc371)",
                                }}
                            >
                                Retry
                            </Button>
                        </Stack>
                    )}

                    {/* Game list */}
                    {!loading && !error && (
                        <Box
                            sx={{
                                mt: 1,
                                borderRadius: 3,
                                border: "1px solid rgba(255,255,255,0.06)",
                                bgcolor: "rgba(7,12,30,0.85)",
                                p: { xs: 2, md: 3 },
                                boxShadow: "0 18px 40px rgba(0,0,0,0.7)",
                            }}
                        >
                            <GameList
                                games={games}
                                onPlay={handlePlay}
                                onViewAchievements={handleViewAchievements}
                            />
                        </Box>
                    )}
                </Stack>
            </Box>
        </Container>
    );
}
