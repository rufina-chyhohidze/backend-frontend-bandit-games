import { useEffect, useState, useContext } from "react";
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
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    List,
    ListItemButton,
    ListItemText,
} from "@mui/material";

import EmojiEventsRoundedIcon from "@mui/icons-material/EmojiEventsRounded";
import ArrowBackRoundedIcon from "@mui/icons-material/ArrowBackRounded";
import CheckCircleRoundedIcon from "@mui/icons-material/CheckCircleRounded";
import LockRoundedIcon from "@mui/icons-material/LockRounded";
import CompareArrowsRoundedIcon from "@mui/icons-material/CompareArrowsRounded";

import type { Achievement } from "../models/achievement";
import type { Game } from "../models/game";
import type { PlayerDto } from "../models/player";
import type { PlayerDtoWithName } from "../models/friendship";

import { fetchAchievements, fetchGames } from "../services/gamesService";
import { useCurrentPlayer } from "../hooks/useFavorites";
import SecurityContext from "../context/SecurityContext";
import { useFriends } from "../hooks/useFriends";

export default function GameAchievementsPage() {
    const { gameId } = useParams<{ gameId: string }>();
    const navigate = useNavigate();

    const [achievements, setAchievements] = useState<Achievement[]>([]);
    const [game, setGame] = useState<Game | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const { player, isLoadingPlayer } = useCurrentPlayer() as {
        player: PlayerDto | null;
        isLoadingPlayer: boolean;
    };

    const { loggedInUser } = useContext(SecurityContext);
    const playerId = loggedInUser?.id ?? null;
    const { data: friends, isLoading: loadingFriends } = useFriends(playerId);

    const [compareOpen, setCompareOpen] = useState(false);
    const openCompare = () => setCompareOpen(true);
    const closeCompare = () => setCompareOpen(false);

    const goCompare = (friendId: string) => {
        if (!gameId) return;
        closeCompare();
        navigate(`/games/${gameId}/compare/${friendId}`);
    };

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

    const isLoading = loading || isLoadingPlayer;

    const unlockedSet = new Set(player?.achievements ?? []);
    const total = achievements.length;
    const unlockedCount = achievements.filter((a) =>
        unlockedSet.has(a.achievementId)
    ).length;

    const hasFriends = !!friends?.length;

    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100vw",
                display: "flex",
                alignItems: "flex-start",
                justifyContent: "center",
                position: "relative",
                overflow: "hidden",
                background: "linear-gradient(135deg, #141e30 0%, #243b55 100%)",
                pt: { xs: 9, sm: 10 },
                pb: 4,
            }}
        >
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

            <Box
                sx={{
                    position: "relative",
                    zIndex: 1,
                    width: { xs: "97vw", sm: 600, md: 800 },
                    maxWidth: "97vw",
                    borderRadius: 3,
                    p: { xs: 3, md: 4 },
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
                    direction={{ xs: "column", md: "row" }}
                    alignItems={{ xs: "flex-start", md: "center" }}
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

                    <Stack direction={{ xs: "column", sm: "row" }} spacing={1.2} width={{ xs: "100%", md: "auto" }}>
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

                        <Button
                            variant="contained"
                            onClick={() => navigate(`/games/${gameId}/unlocked`)}
                            sx={{ textTransform: "none", borderRadius: 999 }}
                        >
                            My unlocked
                        </Button>

                        <Button
                            variant="contained"
                            startIcon={<CompareArrowsRoundedIcon />}
                            onClick={openCompare}
                            disabled={!hasFriends || loadingFriends}
                            sx={{ textTransform: "none", borderRadius: 999 }}
                        >
                            Compare with friend
                        </Button>
                    </Stack>
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

                                <Stack direction="row" spacing={1}>
                                    <Chip
                                        size="small"
                                        icon={<EmojiEventsRoundedIcon />}
                                        label={`${achievements.length} achievement${
                                            achievements.length === 1 ? "" : "s"
                                        }`}
                                        sx={{
                                            bgcolor: "rgba(0, 0, 0, 0.35)",
                                            color: "#ffecb3",
                                            border: "1px solid rgba(255, 236, 179, 0.7)",
                                            "& .MuiChip-icon": { color: "#ffecb3" },
                                        }}
                                    />

                                    <Chip
                                        size="small"
                                        icon={<CheckCircleRoundedIcon />}
                                        label={total > 0 ? `${unlockedCount}/${total} unlocked` : "No achievements"}
                                        sx={{
                                            bgcolor: "rgba(0, 0, 0, 0.35)",
                                            color: "#c8ffe9",
                                            border: "1px solid rgba(200, 255, 233, 0.7)",
                                            "& .MuiChip-icon": { color: "#c8ffe9" },
                                        }}
                                    />
                                </Stack>
                            </Stack>
                        </CardContent>
                    </Card>
                )}

                <Divider sx={{ borderColor: "rgba(255,255,255,0.11)" }} />

                {isLoading && (
                    <Box display="flex" justifyContent="center" mt={3}>
                        <CircularProgress sx={{ color: "white" }} />
                    </Box>
                )}

                {error && !isLoading && (
                    <Alert severity="error" sx={{ mt: 1 }}>
                        {error}
                    </Alert>
                )}

                {!isLoading && !error && achievements.length === 0 && (
                    <Typography variant="body2" sx={{ color: "#cccccc", mt: 1 }}>
                        No achievements defined for this game yet.
                    </Typography>
                )}

                {!isLoading && !error && achievements.length > 0 && (
                    <Stack spacing={2.0} mt={1}>
                        {achievements.map((a, index) => {
                            const unlocked = unlockedSet.has(a.achievementId);

                            return (
                                <Card
                                    key={a.achievementId}
                                    sx={{
                                        backgroundColor: unlocked
                                            ? "rgba(8, 32, 32, 0.99)"
                                            : "rgba(8, 12, 32, 0.99)",
                                        borderRadius: 2,
                                        boxShadow: unlocked
                                            ? "0 10px 26px rgba(0,255,180,0.35)"
                                            : "0 10px 26px rgba(0,0,0,0.45)",
                                        border: unlocked ? "1px solid rgba(0,255,180,0.5)" : "none",
                                        "&:hover": {
                                            transform: "translateY(-2px)",
                                            boxShadow: unlocked
                                                ? "0 18px 38px rgba(0,255,180,0.4)"
                                                : "0 18px 38px rgba(124,140,255,0.16)",
                                        },
                                        transition: "all 0.18s ease-out",
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
                                                        color: unlocked ? "#80ffb4" : "#ffd54f",
                                                        letterSpacing: 1,
                                                        fontSize: 11,
                                                    }}
                                                >
                                                    ACHIEVEMENT #{index + 1}
                                                </Typography>

                                                <Typography variant="subtitle1" sx={{ color: "white", fontWeight: 600 }}>
                                                    {a.name}
                                                </Typography>

                                                <Typography variant="body2" sx={{ color: "#cfd2ff", mt: 0.5 }}>
                                                    {a.description}
                                                </Typography>

                                                <Typography
                                                    variant="body2"
                                                    sx={{
                                                        mt: 1.2,
                                                        color: unlocked ? "#b2ffda" : "#9dd0ff",
                                                    }}
                                                >
                                                    <strong>How to unlock:</strong> {a.unlockHint}
                                                </Typography>
                                            </Box>

                                            <Chip
                                                icon={unlocked ? <CheckCircleRoundedIcon /> : <LockRoundedIcon />}
                                                label={unlocked ? "Unlocked" : "Locked"}
                                                sx={{
                                                    alignSelf: "flex-start",
                                                    bgcolor: unlocked
                                                        ? "rgba(0, 255, 180, 0.18)"
                                                        : "rgba(255, 255, 255, 0.06)",
                                                    color: unlocked ? "#b2ffda" : "#e0e0e0",
                                                    "& .MuiChip-icon": { color: unlocked ? "#b2ffda" : "#e0e0e0" },
                                                }}
                                            />
                                        </Stack>
                                    </CardContent>
                                </Card>
                            );
                        })}
                    </Stack>
                )}

                <Dialog open={compareOpen} onClose={closeCompare} fullWidth maxWidth="sm">
                    <DialogTitle>Select a friend to compare</DialogTitle>

                    <DialogContent dividers>
                        {loadingFriends && (
                            <Box display="flex" justifyContent="center" py={2}>
                                <CircularProgress />
                            </Box>
                        )}

                        {!loadingFriends && (!friends || friends.length === 0) && (
                            <Alert severity="info">You have no friends to compare with yet.</Alert>
                        )}

                        {!loadingFriends && friends?.length ? (
                            <List disablePadding>
                                {friends.map((f: PlayerDtoWithName) => (
                                    <ListItemButton key={f.playerId} onClick={() => goCompare(f.playerId)}>
                                        <ListItemText
                                            primary={f.username}
                                            secondary="Compare achievements for this game"
                                        />
                                    </ListItemButton>
                                ))}
                            </List>
                        ) : null}
                    </DialogContent>

                    <DialogActions>
                        <Button onClick={closeCompare} sx={{ textTransform: "none" }}>
                            Cancel
                        </Button>
                    </DialogActions>
                </Dialog>
            </Box>
        </Box>
    );
}
