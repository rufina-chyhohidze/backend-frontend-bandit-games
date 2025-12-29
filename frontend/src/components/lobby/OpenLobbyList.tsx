import {
    Box,
    Chip,
    CircularProgress,
    Typography,
    Stack,
    Button,
    Card,
    CardContent,
} from "@mui/material";
import GamepadRoundedIcon from "@mui/icons-material/GamepadRounded";
import PersonIcon from "@mui/icons-material/Person";
import GroupIcon from "@mui/icons-material/Group";
import type { LobbyDto } from "../../models/lobby";
import { usePlayerById } from "../../hooks/usePlayer";

interface OpenLobbiesListProps {
    openLobbies: LobbyDto[];
    isLoading: boolean;
    isError: boolean;
    onJoinLobby: (lobbyId: string) => void;
    currentUserId?: string;
}

function HostName({ playerId }: { playerId: string }) {
    const { data: player } = usePlayerById(playerId);
    return (
        <Typography
            variant="caption"
            sx={{
                color: "#fff",
                fontWeight: 800,
                textShadow: "0 1px 8px rgba(0,0,0,0.55)",
            }}
        >
            {player?.username ?? playerId}
        </Typography>
    );
}

export function OpenLobbiesList({
                                    openLobbies,
                                    isLoading,
                                    isError,
                                    onJoinLobby,
                                    currentUserId,
                                }: OpenLobbiesListProps) {
    const renderContent = () => {
        if (isLoading) {
            return (
                <Stack direction="row" spacing={2} justifyContent="center" py={4} alignItems="center">
                    <CircularProgress size={24} sx={{ color: "#9d7dff" }} />
                    <Typography variant="body2" sx={{ color: "rgba(255,255,255,0.85)", fontWeight: 700 }}>
                        Loading open lobbies...
                    </Typography>
                </Stack>
            );
        }

        if (isError) {
            return (
                <Box sx={{ textAlign: "center", py: 4 }}>
                    <Typography color="error" variant="body2" sx={{ fontWeight: 800 }}>
                        Failed to load lobbies
                    </Typography>
                    <Typography variant="caption" sx={{ color: "rgba(255,255,255,0.75)", mt: 1, display: "block" }}>
                        Please try again later
                    </Typography>
                </Box>
            );
        }

        if (openLobbies.length === 0) {
            return (
                <Box sx={{ textAlign: "center", py: 6 }}>
                    <GamepadRoundedIcon sx={{ fontSize: 48, color: "rgba(255,255,255,0.45)", mb: 2 }} />
                    <Typography variant="body2" sx={{ color: "rgba(255,255,255,0.82)", fontWeight: 700 }}>
                        No open lobbies available
                    </Typography>
                    <Typography variant="caption" sx={{ color: "rgba(255,255,255,0.65)", mt: 1, display: "block" }}>
                        Create one to get started!
                    </Typography>
                </Box>
            );
        }

        return (
            <Stack spacing={2} sx={{ maxHeight: 450, overflowY: "auto", pr: 1 }}>
                {openLobbies.map((lobby) => {
                    const isInThisLobby =
                        !!currentUserId &&
                        (lobby.hostPlayerId === currentUserId || lobby.guestPlayerId === currentUserId);

                    const isHost = !!currentUserId && lobby.hostPlayerId === currentUserId;

                    const hasAiGuest =
                        !!lobby.guestType &&
                        (lobby.guestType === "AI_EASY" ||
                            lobby.guestType === "AI_MEDIUM" ||
                            lobby.guestType === "AI_HARD" ||
                            lobby.guestType.startsWith("AI_"));

                    const isFull = !!lobby.guestPlayerId || hasAiGuest;
                    const playersCount = isFull ? 2 : 1;

                    const fullReason = hasAiGuest ? "AI opponent selected" : "Lobby is full";

                    return (
                        <Card
                            key={lobby.lobbyId}
                            sx={{
                                bgcolor: "rgba(255, 255, 255, 0.06)",
                                border: "1px solid rgba(255, 255, 255, 0.12)",
                                borderRadius: 2,
                                transition: "all 0.2s ease",
                                "&:hover": {
                                    bgcolor: "rgba(255, 255, 255, 0.09)",
                                    borderColor: "rgba(157, 125, 255, 0.45)",
                                    transform: "translateY(-2px)",
                                },
                            }}
                        >
                            <CardContent sx={{ p: 2, "&:last-child": { pb: 2 } }}>
                                <Stack spacing={1.5}>
                                    <Stack direction="row" justifyContent="space-between" alignItems="center">
                                        <Stack direction="row" spacing={1} alignItems="center">
                                            <GamepadRoundedIcon sx={{ fontSize: 20, color: "#9d7dff" }} />
                                            <Typography
                                                variant="subtitle2"
                                                sx={{
                                                    color: "#fff",
                                                    fontWeight: 900,
                                                    fontFamily: "monospace",
                                                    textShadow: "0 1px 10px rgba(0,0,0,0.6)",
                                                }}
                                            >
                                                #{lobby.lobbyId.substring(0, 8).toUpperCase()}
                                            </Typography>
                                        </Stack>

                                        <Chip
                                            label={isFull ? "Full" : "Open"}
                                            size="small"
                                            sx={{
                                                bgcolor: isFull ? "rgba(220, 53, 69, 0.22)" : "rgba(56, 161, 105, 0.22)",
                                                color: "#fff",
                                                fontWeight: 900,
                                                fontSize: "0.7rem",
                                                height: 20,
                                                borderRadius: 1,
                                                border: isFull
                                                    ? "1px solid rgba(255, 255, 255, 0.22)"
                                                    : "1px solid rgba(255, 255, 255, 0.18)",
                                                textShadow: "0 1px 8px rgba(0,0,0,0.55)",
                                            }}
                                        />
                                    </Stack>

                                    <Stack direction="row" spacing={1} alignItems="center">
                                        <PersonIcon sx={{ fontSize: 16, color: "#9d7dff" }} />
                                        <Typography variant="caption" sx={{ color: "rgba(255,255,255,0.92)", fontWeight: 800 }}>
                                            Host:
                                        </Typography>
                                        <HostName playerId={lobby.hostPlayerId} />
                                    </Stack>

                                    <Stack direction="row" spacing={1} alignItems="center">
                                        <GroupIcon sx={{ fontSize: 16, color: "#9d7dff" }} />
                                        <Typography variant="caption" sx={{ color: "rgba(255,255,255,0.92)", fontWeight: 800 }}>
                                            Players:
                                        </Typography>
                                        <Typography
                                            variant="caption"
                                            sx={{
                                                color: "#fff",
                                                fontWeight: 900,
                                                textShadow: "0 1px 8px rgba(0,0,0,0.55)",
                                            }}
                                        >
                                            {playersCount} / 2
                                        </Typography>
                                    </Stack>

                                    {!isInThisLobby ? (
                                        <Button
                                            variant="contained"
                                            fullWidth
                                            size="small"
                                            onClick={() => onJoinLobby(lobby.lobbyId)}
                                            disabled={isFull || isHost}
                                            sx={{
                                                mt: 1,
                                                bgcolor: "#9d7dff",
                                                "&:hover": { bgcolor: "#7f5aff" },
                                                textTransform: "none",
                                                fontWeight: 900,
                                                py: 1,
                                                borderRadius: 1.5,

                                                "&.Mui-disabled": {
                                                    bgcolor: "rgba(255,255,255,0.12)",
                                                    color: "#fff",
                                                    opacity: 1,
                                                    border: "1px solid rgba(255,255,255,0.18)",
                                                    textShadow: "0 1px 10px rgba(0,0,0,0.65)",
                                                },
                                            }}
                                        >
                                            {isHost ? "You are the host" : isFull ? fullReason : "Join Lobby"}
                                        </Button>
                                    ) : (
                                        <Chip
                                            label={isHost ? "You are the host" : "You are in this lobby"}
                                            size="small"
                                            sx={{
                                                mt: 1,
                                                bgcolor: "rgba(157, 125, 255, 0.22)",
                                                color: "#fff",
                                                fontWeight: 900,
                                                border: "1px solid rgba(255,255,255,0.16)",
                                                textShadow: "0 1px 10px rgba(0,0,0,0.6)",
                                            }}
                                        />
                                    )}
                                </Stack>
                            </CardContent>
                        </Card>
                    );
                })}
            </Stack>
        );
    };

    return (
        <Box
            sx={{
                bgcolor: "rgba(0, 0, 0, 0.28)",
                borderRadius: 3,
                p: 3,
                border: "1px solid rgba(255, 255, 255, 0.12)",
                minHeight: 300,
                height: "fit-content",
            }}
        >
            <Stack direction="row" alignItems="center" spacing={1.5} sx={{ mb: 2.5 }}>
                <GamepadRoundedIcon sx={{ fontSize: 24, color: "#9d7dff" }} />
                <Typography
                    variant="h6"
                    sx={{
                        color: "#fff",
                        fontWeight: 900,
                        textShadow: "0 1px 12px rgba(0,0,0,0.6)",
                    }}
                >
                    Open Lobbies
                </Typography>

                {!isLoading && !isError && (
                    <Chip
                        label={openLobbies.length}
                        size="small"
                        sx={{
                            bgcolor: "rgba(157, 125, 255, 0.22)",
                            color: "#fff",
                            fontWeight: 900,
                            fontSize: "0.75rem",
                            height: 22,
                            border: "1px solid rgba(255,255,255,0.16)",
                            textShadow: "0 1px 10px rgba(0,0,0,0.6)",
                        }}
                    />
                )}
            </Stack>

            {renderContent()}
        </Box>
    );
}
