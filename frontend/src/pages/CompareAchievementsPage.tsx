import { useNavigate, useParams } from "react-router-dom";
import {
    Box,
    Typography,
    CircularProgress,
    Alert,
    Button,
    Stack,
    Divider,
    Chip,
    Card,
    CardContent,
} from "@mui/material";
import ArrowBackRoundedIcon from "@mui/icons-material/ArrowBackRounded";
import CompareArrowsRoundedIcon from "@mui/icons-material/CompareArrowsRounded";
import PersonRoundedIcon from "@mui/icons-material/PersonRounded";

import {
    useMyUnlockedAchievements,
    useFriendUnlockedAchievements,
} from "../hooks/useUnlockedAchievements";
import { UnlockedAchievementsList } from "../components/achievements/UnlockedAchievementsList";
import { useGames } from "../hooks/useGames";

export function CompareAchievementsPage() {
    const { gameId, friendId } = useParams<{ gameId: string; friendId: string }>();
    const navigate = useNavigate();

    const { games } = useGames();
    const game = games?.find((g) => g.gameId === gameId) ?? null;

    const meQ = useMyUnlockedAchievements(gameId);
    const friendQ = useFriendUnlockedAchievements(friendId, gameId);

    if (!gameId || !friendId) return <Alert severity="error">Missing parameters.</Alert>;

    const loading = meQ.isLoading || friendQ.isLoading;
    const error = (meQ.isError && meQ.error) || (friendQ.isError && friendQ.error);

    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100%",
                background: "linear-gradient(135deg, #141e30 0%, #243b55 100%)",
                display: "flex",
                justifyContent: "center",
                pt: { xs: 9, sm: 10 },
                pb: 4,
            }}
        >
            <Box
                sx={{
                    width: { xs: "97vw", md: 1100 },
                    maxWidth: "97vw",
                    borderRadius: 3,
                    p: { xs: 3, md: 4 },
                    bgcolor: "rgba(7, 11, 25, 0.98)",
                    boxShadow: "0 24px 55px rgba(0,0,0,0.85)",
                    color: "white",
                }}
            >
                <Stack spacing={2.5}>
                    {/* Header */}
                    <Stack direction="row" alignItems="center" justifyContent="space-between" gap={2}>
                        <Stack direction="row" alignItems="center" spacing={1.5}>
                            <CompareArrowsRoundedIcon sx={{ fontSize: 32, color: "#ffd54f" }} />
                            <Box>
                                <Typography variant="h5" sx={{ fontWeight: 800 }}>
                                    Compare Achievements
                                </Typography>
                                <Typography variant="body2" sx={{ color: "#c3c6ff", mt: 0.3 }}>
                                    {game ? game.name : "Game"} • Me vs Friend
                                </Typography>
                            </Box>
                        </Stack>

                        <Button
                            variant="outlined"
                            onClick={() => navigate(`/games/${gameId}/achievements`)}
                            startIcon={<ArrowBackRoundedIcon />}
                            sx={{
                                borderColor: "rgba(255,255,255,0.6)",
                                color: "white",
                                textTransform: "none",
                                borderRadius: 999,
                                "&:hover": {
                                    borderColor: "#fff",
                                    backgroundColor: "rgba(255,255,255,0.10)",
                                },
                            }}
                        >
                            Back
                        </Button>
                    </Stack>

                    <Divider sx={{ borderColor: "rgba(255,255,255,0.11)" }} />

                    {loading && (
                        <Box display="flex" justifyContent="center" py={4}>
                            <CircularProgress sx={{ color: "white" }} />
                        </Box>
                    )}

                    {!!error && !loading && (
                        <Alert severity="error">
                            {(error as any)?.response?.data?.message ??
                                (error as any)?.message ??
                                "Failed to load comparison"}
                        </Alert>
                    )}

                    {!loading && !error && (
                        <Box
                            sx={{
                                display: "grid",
                                gridTemplateColumns: { xs: "1fr", md: "1fr 1fr" }, // <-- 2 columns on desktop
                                gap: 2.5,
                                alignItems: "start",
                            }}
                        >
                            <Card
                                elevation={0}
                                sx={{
                                    height: { xs: "auto", md: "70vh" },
                                    display: "flex",
                                    flexDirection: "column",
                                    borderRadius: 3,
                                    bgcolor: "rgba(255,255,255,0.05)",
                                    border: "1px solid rgba(124,255,197,0.25)",
                                    boxShadow: "0 18px 45px rgba(0,0,0,0.45)",
                                    minWidth: 0,
                                }}
                            >
                                <CardContent sx={{ display: "flex", flexDirection: "column", gap: 1.5 }}>
                                    <Stack direction="row" alignItems="center" justifyContent="space-between" gap={2}>
                                        <Stack direction="row" alignItems="center" spacing={1}>
                                            <PersonRoundedIcon sx={{ color: "#7cffc5" }} />
                                            <Typography
                                                sx={{
                                                    fontWeight: 800,
                                                    color: "#ffffff",
                                                }}
                                            >
                                                Me
                                            </Typography>

                                        </Stack>
                                        <Chip
                                            label={`${meQ.data?.length ?? 0} unlocked`}
                                            sx={{
                                                bgcolor: "rgba(0, 220, 130, 0.18)",
                                                color: "#7cffc5",
                                                border: "1px solid rgba(124, 255, 197, 0.4)",
                                            }}
                                        />
                                    </Stack>

                                    <Divider sx={{ borderColor: "rgba(124,255,197,0.18)" }} />

                                    <Box
                                        sx={{
                                            mt: 0.5,
                                            overflowY: { md: "auto" },
                                            maxHeight: { md: "58vh" },
                                            pr: { md: 1 },
                                        }}
                                    >
                                        <UnlockedAchievementsList achievements={meQ.data ?? []} />
                                    </Box>
                                </CardContent>
                            </Card>

                            <Card
                                elevation={0}
                                sx={{
                                    height: { xs: "auto", md: "70vh" },
                                    display: "flex",
                                    flexDirection: "column",
                                    borderRadius: 3,
                                    bgcolor: "rgba(255,255,255,0.05)",
                                    border: "1px solid rgba(255,213,79,0.25)",
                                    boxShadow: "0 18px 45px rgba(0,0,0,0.45)",
                                    minWidth: 0,
                                }}
                            >
                                <CardContent sx={{ display: "flex", flexDirection: "column", gap: 1.5 }}>
                                    <Stack direction="row" alignItems="center" justifyContent="space-between" gap={2}>
                                        <Stack direction="row" alignItems="center" spacing={1}>
                                            <PersonRoundedIcon sx={{ color: "#ffd54f" }} />
                                            <Typography
                                                sx={{
                                                    fontWeight: 800,
                                                    color: "#ffffff",
                                                }}
                                            >
                                                Friend
                                            </Typography>

                                        </Stack>
                                        <Chip
                                            label={`${friendQ.data?.length ?? 0} unlocked`}
                                            sx={{
                                                bgcolor: "rgba(255, 213, 79, 0.18)",
                                                color: "#ffd54f",
                                                border: "1px solid rgba(255, 213, 79, 0.4)",
                                            }}
                                        />
                                    </Stack>

                                    <Divider sx={{ borderColor: "rgba(255,213,79,0.18)" }} />

                                    <Box
                                        sx={{
                                            mt: 0.5,
                                            overflowY: { md: "auto" },
                                            maxHeight: { md: "58vh" },
                                            pr: { md: 1 },
                                        }}
                                    >
                                        <UnlockedAchievementsList achievements={friendQ.data ?? []} />
                                    </Box>
                                </CardContent>
                            </Card>
                        </Box>
                    )}
                </Stack>
            </Box>
        </Box>
    );
}
