import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
    Box,
    Typography,
    CircularProgress,
    Alert,
    Card,
    CardContent,
    Button,
    Stack,
    Divider,
    Chip,
} from "@mui/material";
import EmojiEventsRoundedIcon from "@mui/icons-material/EmojiEventsRounded";
import ArrowBackRoundedIcon from "@mui/icons-material/ArrowBackRounded";
import type { Achievement } from "../models/achievement.ts";
import type { Game } from "../models/game.ts";
import { fetchAchievements, fetchGames } from "../services/gamesService.ts";
import { TopNavBar } from "../components/layout/TopNavBar";

export default function GameAchievementsPage() {
    const { gameId } = useParams<{ gameId: string }>();
    const navigate = useNavigate();

    const [achievements, setAchievements] = useState<Achievement[]>([]);
    const [game, setGame] = useState<Game | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (!gameId) return;

        async function loadData() {
            try {
                setLoading(true);
                setError(null);

                const games = await fetchGames();
                const current = games.find((g) => g.gameId === gameId) || null;
                setGame(current);

                const data = await fetchAchievements(gameId);
                setAchievements(data);
            } catch (e: any) {
                console.error(e);
                setError(e.message ?? "Failed to load achievements");
            } finally {
                setLoading(false);
            }
        }

        loadData();
    }, [gameId]);

    if (!gameId) {
        return <Alert severity="error">Missing game id.</Alert>;
    }

    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100vw",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                position: "relative",
                overflow: "hidden",
                background: "linear-gradient(135deg, #141e30 0%, #243b55 100%)",
            }}
        >
            {/* Top navigation bar */}
            <Box
                sx={{
                    position: "absolute",
                    top: 0,
                    left: 0,
                    right: 0,
                    zIndex: 2,
                }}
            >
                <TopNavBar />
            </Box>

            {/* Glow overlay */}
            <Box
                sx={{
                    position: "absolute",
                    inset: 0,
                    pointerEvents: "none",
                    background:
                        "radial-gradient(circle at 10% 0%, rgba(255,215,0,0.13), transparent 60%), " +
                        "radial-gradient(circle at 90% 100%, rgba(124,140,255,0.24), transparent 60%)",
                    opacity: 0.85,
                    zIndex: 0,
                }}
            />

            {/* Main card */}
            <Box
                sx={{
                    position: "relative",
                    zIndex: 1,
                    width: { xs: "97vw", sm: 600, md: 800 },
                    maxWidth: "97vw",
                    borderRadius: 3,
                    p: { xs: 3, md: 4 }, // slightly more padding to compensate for navbar
                    pt: { xs: 5, md: 6 },
                    bgcolor: "rgba(7, 11, 25, 0.98)",
                    boxShadow: "0 24px 55px rgba(0,0,0,0.85)",
                    color: "white",
                    display: "flex",
                    flexDirection: "column",
                    gap: 2.5,
                }}
            >
                <Stack
                    direction="row"
                    alignItems="center"
                    justifyContent="space-between"
                    gap={2}
                >
                    <Stack direction="row" alignItems="center" spacing={1.5}>
                        <EmojiEventsRoundedIcon sx={{ fontSize: 32, color: "#ffd54f" }} />
                        <Box>
                            <Typography variant="h5" sx={{ fontWeight: 700, letterSpacing: 0.4 }}>
                                Achievements
                            </Typography>
                            <Typography variant="body2" sx={{ color: "#c3c6ff", mt: 0.3 }}>
                                Unlock challenges and track your progress.
                            </Typography>
                        </Box>
                    </Stack>
                    <Button
                        variant="outlined"
                        onClick={() => navigate("/games")}
                        startIcon={<ArrowBackRoundedIcon />}
                        sx={{
                            borderColor: "rgba(255,255,255,0.6)",
                            color: "white",
                            textTransform: "none",
                            fontWeight: 500,
                            borderRadius: 999,
                            px: 2.3,
                            "&:hover": {
                                borderColor: "#ffffff",
                                backgroundColor: "rgba(255,255,255,0.10)",
                            },
                        }}
                    >
                        Back to games
                    </Button>
                </Stack>

                {game && (
                    <Card
                        sx={{
                            borderRadius: 2.5,
                            background:
                                "linear-gradient(135deg, rgba(124,140,255,0.14), rgba(0,220,130,0.16))",
                            boxShadow: "0 10px 32px rgba(0,0,0,0.65)",
                            border: "none",
                        }}
                        elevation={0}
                    >
                        <CardContent>
                            <Stack
                                direction={{ xs: "column", md: "row" }}
                                justifyContent="space-between"
                                alignItems={{ xs: "flex-start", md: "center" }}
                                gap={2}
                            >
                                <Box>
                                    <Typography variant="h6" sx={{ fontWeight: 600, color: "white" }}>
                                        {game.name}
                                    </Typography>
                                    <Typography variant="body2" sx={{ color: "#f1f1ff", mt: 0.5 }}>
                                        {game.description}
                                    </Typography>
                                </Box>
                                <Chip
                                    size="small"
                                    icon={<EmojiEventsRoundedIcon />}
                                    label={`${achievements.length} achievement${achievements.length === 1 ? "" : "s"}`}
                                    sx={{
                                        bgcolor: "rgba(0, 0, 0, 0.35)",
                                        color: "#ffecb3",
                                        border: "1px solid rgba(255, 236, 179, 0.7)",
                                        "& .MuiChip-icon": {
                                            color: "#ffecb3",
                                        },
                                    }}
                                />
                            </Stack>
                        </CardContent>
                    </Card>
                )}

                <Divider sx={{ borderColor: "rgba(255,255,255,0.11)" }} />

                {loading && (
                    <Box display="flex" justifyContent="center" mt={3}>
                        <CircularProgress sx={{ color: "white" }} />
                    </Box>
                )}

                {error && !loading && (
                    <Alert severity="error" sx={{ mt: 1 }}>
                        {error}
                    </Alert>
                )}

                {!loading && !error && achievements.length === 0 && (
                    <Typography variant="body2" sx={{ color: "#cccccc", mt: 1 }}>
                        No achievements defined for this game yet.
                    </Typography>
                )}

                {!loading && !error && achievements.length > 0 && (
                    <Stack spacing={2.0} mt={1}>
                        {achievements.map((a, index) => (
                            <Card
                                key={a.achievementId}
                                sx={{
                                    backgroundColor: "rgba(8, 12, 32, 0.99)",
                                    borderRadius: 2,
                                    boxShadow: "0 10px 26px rgba(0,0,0,0.45)",
                                    border: "none",
                                    "&:hover": {
                                        transform: "translateY(-2px)",
                                        boxShadow: "0 18px 38px rgba(124,140,255,0.16)",
                                    },
                                }}
                                elevation={0}
                            >
                                <CardContent>
                                    <Stack
                                        direction="row"
                                        justifyContent="space-between"
                                        alignItems="flex-start"
                                        gap={2}
                                    >
                                        <Box>
                                            <Typography
                                                variant="overline"
                                                sx={{
                                                    color: "#ffd54f",
                                                    letterSpacing: 1,
                                                    fontSize: 11,
                                                }}
                                            >
                                                ACHIEVEMENT #{index + 1}
                                            </Typography>
                                            <Typography
                                                variant="subtitle1"
                                                sx={{ color: "white", fontWeight: 600 }}
                                            >
                                                {a.name}
                                            </Typography>
                                            <Typography
                                                variant="body2"
                                                sx={{ color: "#cfd2ff", mt: 0.5 }}
                                            >
                                                {a.description}
                                            </Typography>
                                            <Typography
                                                variant="body2"
                                                sx={{ mt: 1.2, color: "#9dd0ff" }}
                                            >
                                                <strong>How to unlock:</strong> {a.unlockHint}
                                            </Typography>
                                        </Box>
                                    </Stack>
                                </CardContent>
                            </Card>
                        ))}
                    </Stack>
                )}
            </Box>
        </Box>
    );
}
