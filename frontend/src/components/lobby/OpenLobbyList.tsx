import {
    Box,
    Typography,
    Stack,
    CircularProgress,
    Button,
    Card,
    CardContent,
    Chip,
} from "@mui/material";
import GamepadRoundedIcon from '@mui/icons-material/GamepadRounded';
import PersonIcon from '@mui/icons-material/Person';
import GroupIcon from '@mui/icons-material/Group';
import { type LobbyDto } from "../../models/lobby";
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
        <Typography variant="caption" sx={{ color: "#fff", fontWeight: 500 }}>
            {player?.username ?? playerId}
        </Typography>
    );
}

export function OpenLobbiesList({ openLobbies, isLoading, isError, onJoinLobby, currentUserId }: OpenLobbiesListProps) {

    const renderContent = () => {
        if (isLoading) {
            return (
                <Stack direction="row" spacing={2} justifyContent="center" py={4} alignItems="center">
                    <CircularProgress size={24} sx={{color: "#9d7dff"}}/>
                    <Typography variant="body2" sx={{color: "#d0d0e5"}}>
                        Loading open lobbies...
                    </Typography>
                </Stack>
            );
        }

        if (isError) {
            return (
                <Box sx={{textAlign: "center", py: 4}}>
                    <Typography color="error" variant="body2">
                        Failed to load lobbies
                    </Typography>
                    <Typography variant="caption" sx={{color: "#aaa", mt: 1, display: "block"}}>
                        Please try again later
                    </Typography>
                </Box>
            );
        }

        if (openLobbies.length === 0) {
            return (
                <Box sx={{textAlign: "center", py: 6}}>
                    <GamepadRoundedIcon sx={{fontSize: 48, color: "#555", mb: 2}}/>
                    <Typography variant="body2" sx={{color: "#aaa"}}>
                        No open lobbies available
                    </Typography>
                    <Typography variant="caption" sx={{color: "#777", mt: 1, display: "block"}}>
                        Create one to get started!
                    </Typography>
                </Box>
            );
        }

        return (
            <Stack spacing={2} sx={{maxHeight: 450, overflowY: 'auto', pr: 1}}>
                {openLobbies.map((lobby) => {
                    const isInThisLobby =
                        !!currentUserId &&
                        (lobby.hostPlayerId === currentUserId ||
                            lobby.guestPlayerId === currentUserId);

                    const playersCount =
                        lobby.guestPlayerId || (lobby.guestType && lobby.guestType.startsWith("AI_"))
                            ? 2
                            : 1;

                    return (
                        <Card
                            key={lobby.lobbyId}
                            sx={{
                                bgcolor: "rgba(255, 255, 255, 0.05)",
                                border: "1px solid rgba(255, 255, 255, 0.1)",
                                borderRadius: 2,
                                transition: "all 0.2s ease",
                                '&:hover': {
                                    bgcolor: "rgba(255, 255, 255, 0.08)",
                                    borderColor: "rgba(157, 125, 255, 0.4)",
                                    transform: "translateY(-2px)",
                                }
                            }}
                        >
                            <CardContent sx={{p: 2, '&:last-child': {pb: 2}}}>
                                <Stack spacing={1.5}>
                                    {/* Header with Lobby ID and Status */}
                                    <Stack direction="row" justifyContent="space-between" alignItems="center">
                                        <Stack direction="row" spacing={1} alignItems="center">
                                            <GamepadRoundedIcon sx={{fontSize: 20, color: "#9d7dff"}}/>
                                            <Typography
                                                variant="subtitle2"
                                                sx={{
                                                    color: "#fff",
                                                    fontWeight: 600,
                                                    fontFamily: 'monospace'
                                                }}
                                            >
                                                #{lobby.lobbyId.substring(0, 8).toUpperCase()}
                                            </Typography>
                                        </Stack>
                                        <Chip
                                            label="Open"
                                            size="small"
                                            sx={{
                                                bgcolor: "rgba(56, 161, 105, 0.2)",
                                                color: "#68d391",
                                                fontWeight: 600,
                                                fontSize: "0.7rem",
                                                height: 20,
                                                borderRadius: 1,
                                            }}
                                        />
                                    </Stack>

                                    <Stack direction="row" spacing={1} alignItems="center">
                                        <PersonIcon sx={{fontSize: 16, color: "#9d7dff"}}/>
                                        <Typography variant="caption" sx={{color: "#d0d0e5"}}>
                                            Host:
                                        </Typography>
                                        <HostName playerId={lobby.hostPlayerId} />
                                    </Stack>

                                    <Stack direction="row" spacing={1} alignItems="center">
                                        <GroupIcon sx={{fontSize: 16, color: "#9d7dff"}}/>
                                        <Typography variant="caption" sx={{color: "#d0d0e5"}}>
                                            Players:
                                        </Typography>
                                        <Typography variant="caption" sx={{color: "#fff", fontWeight: 500}}>
                                            {playersCount} / 2
                                        </Typography>
                                    </Stack>

                                    {!isInThisLobby ? (
                                        <Button
                                            variant="contained"
                                            fullWidth
                                            size="small"
                                            onClick={() => onJoinLobby(lobby.lobbyId)}
                                            sx={{
                                                mt: 1,
                                                bgcolor: "#9d7dff",
                                                '&:hover': {bgcolor: '#7f5aff'},
                                                textTransform: 'none',
                                                fontWeight: 600,
                                                py: 1,
                                                borderRadius: 1.5,
                                            }}
                                        >
                                            Join Lobby
                                        </Button>
                                    ) : (
                                        <Chip
                                            label={
                                                lobby.hostPlayerId === currentUserId
                                                    ? "You are the host"
                                                    : "You are in this lobby"
                                            }
                                            size="small"
                                            sx={{
                                                mt: 1,
                                                bgcolor: "rgba(157, 125, 255, 0.2)",
                                                color: "#e3ddff",
                                                fontWeight: 600,
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
                bgcolor: "rgba(0, 0, 0, 0.3)",
                borderRadius: 3,
                p: 3,
                border: "1px solid rgba(255, 255, 255, 0.1)",
                minHeight: 300,
                height: "fit-content",
            }}
        >
            <Stack direction="row" alignItems="center" spacing={1.5} sx={{mb: 2.5}}>
                <GamepadRoundedIcon sx={{fontSize: 24, color: "#9d7dff"}}/>
                <Typography variant="h6" sx={{color: "#fff", fontWeight: 700}}>
                    Open Lobbies
                </Typography>
                {!isLoading && !isError && (
                    <Chip
                        label={openLobbies.length}
                        size="small"
                        sx={{
                            bgcolor: "rgba(157, 125, 255, 0.2)",
                            color: "#9d7dff",
                            fontWeight: 600,
                            fontSize: "0.75rem",
                            height: 22,
                        }}
                    />
                )}
            </Stack>
            {renderContent()}
        </Box>
    );
}