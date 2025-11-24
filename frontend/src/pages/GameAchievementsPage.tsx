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
import type { Achievement } from "../models/Achievement";
import type { Game } from "../models/Game";
import { fetchAchievements, fetchGames } from "../api/gamesApi";

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
                maxWidth: 960,
                mx: "auto",
                textAlign: "center"
            }}
        >
            {/* Background gradient */}
            <Box
                sx={{
                    position: "absolute",
                    inset: 0,
                    zIndex: 0,
                    pointerEvents: "none",
                    background:
                        "radial-gradient(circle at 10% 0%, rgba(255, 215, 0, 0.16), transparent 55%), radial-gradient(circle at 90% 100%, rgba(157, 125, 255, 0.25), transparent 55%)",
                }}
            />

            <Box
                sx={{
                    position: "relative",
                    zIndex: 1,
                    borderRadius: 4,
                    p: { xs: 3, md: 4 },
                    bgcolor: "rgba(7, 10, 26, 0.96)",
                    border: "1px solid rgba(255,255,255,0.08)",
                    boxShadow: "0 24px 60px rgba(0,0,0,0.9)",
                    backdropFilter: "blur(18px)",
                    color: "white",
                }}
            >
                {/* Header */}
                <Stack
                    direction="row"
                    alignItems="center"
                    justifyContent="space-between"
                    mb={3}
                    gap={2}
                >
                    <Stack direction="row" alignItems="center" spacing={1.5}>
                        <EmojiEventsRoundedIcon
                            sx={{ fontSize: 34, color: "#ffd54f" }}
                        />
                        <Box>
                            <Typography
                                variant="h4"
                                sx={{ fontWeight: 700, letterSpacing: 0.5 }}
                            >
                                Achievements
                            </Typography>
                            <Typography
                                variant="body2"
                                sx={{ color: "#c3c6ff", mt: 0.5 }}
                            >
                                Track the milestones and hidden challenges for this game.
                            </Typography>
                        </Box>
                    </Stack>

                    <Button
                        variant="outlined"
                        onClick={() => navigate("/games")}
                        startIcon={<ArrowBackRoundedIcon />}
                        sx={{
                            borderColor: "rgba(255,255,255,0.65)",
                            color: "white",
                            textTransform: "none",
                            fontWeight: 500,
                            borderRadius: 999,
                            "&:hover": {
                                borderColor: "#ffffff",
                                backgroundColor: "rgba(255,255,255,0.08)",
                            },
                        }}
                    >
                        Back to Games
                    </Button>
                </Stack>

                {/* Game Header */}
                {game && (
                    <Card
                        sx={{
                            mb: 3,
                            borderRadius: 3,
                            background:
                                "linear-gradient(135deg, rgba(157,125,255,0.1), rgba(0,220,130,0.12))",
                            border: "1px solid rgba(255,255,255,0.15)",
                            boxShadow: "0 16px 42px rgba(0,0,0,0.7)",
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
                                    <Typography
                                        variant="h5"
                                        sx={{ fontWeight: 600, color: "white" }}
                                    >
                                        {game.name}
                                    </Typography>
                                    <Typography
                                        variant="body2"
                                        sx={{ color: "#f1f1ff", mt: 0.5 }}
                                    >
                                        {game.description}
                                    </Typography>
                                </Box>

                                <Stack
                                    direction="row"
                                    spacing={1}
                                    flexWrap="wrap"
                                    justifyContent={{ xs: "flex-start", md: "flex-end" }}
                                >
                                    <Chip
                                        size="small"
                                        icon={<EmojiEventsRoundedIcon />}
                                        label={`${achievements.length} achievement${achievements.length === 1 ? "" : "s"
                                        }`}
                                        sx={{
                                            bgcolor: "rgba(0, 0, 0, 0.3)",
                                            color: "#ffecb3",
                                            border: "1px solid rgba(255, 236, 179, 0.7)",
                                            "& .MuiChip-icon": {
                                                color: "#ffecb3",
                                            },
                                        }}
                                    />
                                </Stack>
                            </Stack>
                        </CardContent>
                    </Card>
                )}

                <Divider
                    sx={{
                        my: 2,
                        borderColor: "rgba(255,255,255,0.15)",
                    }}
                />

                {/* Loading */}
                {loading && (
                    <Box display="flex" justifyContent="center" mt={4}>
                        <CircularProgress sx={{ color: "white" }} />
                    </Box>
                )}

                {/* Error */}
                {error && !loading && (
                    <Alert severity="error" sx={{ mt: 2 }}>
                        {error}
                    </Alert>
                )}

                {/* No achievements */}
                {!loading && !error && achievements.length === 0 && (
                    <Typography variant="body1" sx={{ color: "#cccccc", mt: 2 }}>
                        No achievements defined for this game yet. Check back later —
                        new challenges may appear.
                    </Typography>
                )}

                {/* Achievements List */}
                {!loading && !error && achievements.length > 0 && (
                    <Stack spacing={2.5} mt={2}>
                        {achievements.map((a, index) => (
                            <Card
                                key={a.achievementId}
                                sx={{
                                    backgroundColor: "rgba(8, 12, 32, 0.95)",
                                    border: "1px solid rgba(255,255,255,0.12)",
                                    backdropFilter: "blur(8px)",
                                    borderRadius: 3,
                                    transition:
                                        "transform 150ms ease-out, box-shadow 150ms ease-out, border-color 150ms ease-out",
                                    boxShadow: "0 12px 30px rgba(0,0,0,0.65)",
                                    "&:hover": {
                                        transform: "translateY(-3px)",
                                        boxShadow: "0 18px 40px rgba(0,0,0,0.9)",
                                        borderColor: "rgba(255,215,0,0.85)",
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
                                                variant="h6"
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
                                                sx={{ mt: 1.5, color: "#9dd0ff" }}
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
