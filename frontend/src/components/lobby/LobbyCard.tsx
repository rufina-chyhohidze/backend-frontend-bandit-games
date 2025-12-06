import { Card, CardContent, CardActions, Button, Stack, Divider, CircularProgress } from "@mui/material";
import type { LobbyDto } from "../../models/lobby";
import { PlayerDisplay } from "./PlayerDisplay";
import { useLeaveLobby, useCloseLobby } from "../../hooks/useLobby";
import CloseIcon from "@mui/icons-material/CloseRounded";
import LeaveIcon from "@mui/icons-material/ExitToAppRounded";

interface LobbyCardProps {
    lobby: LobbyDto;
    currentUserId: string;
}

export function LobbyCard({ lobby, currentUserId }: LobbyCardProps) {
    const isHost = lobby.hostPlayerId === currentUserId;

    const { mutateAsync: leaveMutate, isPending: isLeaving } = useLeaveLobby();
    const { mutateAsync: closeMutate, isPending: isClosing } = useCloseLobby();

    const handleLeaveOrClose = async () => {
        try {
            if (isHost) await closeMutate(lobby.lobbyId);
            else await leaveMutate({ lobbyId: lobby.lobbyId, playerId: currentUserId });
        } catch (e) {
            console.error("Failed to leave/close lobby", e);
        }
    };

    return (
        <Card sx={{ width: '100%', maxWidth: 700, bgcolor: 'rgba(10,16,36,0.9)', color: '#fff', borderRadius: 4 }}>
            <CardContent>
                <Stack direction="row" justifyContent="space-around" spacing={4} my={2}>
                    <PlayerDisplay
                        playerId={lobby.hostPlayerId}
                        playerType={lobby.hostType}
                        isHost={true}
                        isYou={isHost}
                    />
                    <PlayerDisplay
                        playerId={lobby.guestPlayerId}
                        playerType={lobby.guestType}
                        isHost={false}
                        isYou={lobby.guestPlayerId === currentUserId}
                    />
                </Stack>
            </CardContent>

            <Divider sx={{ bgcolor: 'rgba(255,255,255,0.1)' }} />

            <CardActions sx={{ p: 3, justifyContent: 'flex-end' }}>
                <Button
                    variant="outlined"
                    onClick={handleLeaveOrClose}
                    disabled={isLeaving || isClosing}
                    startIcon={
                        isLeaving || isClosing ? (
                            <CircularProgress size={20} color="inherit" />
                        ) : isHost ? (
                            <CloseIcon />
                        ) : (
                            <LeaveIcon />
                        )
                    }
                    sx={{ textTransform: 'none', borderRadius: 100, color: '#fff' }}
                >
                    {isHost ? (isClosing ? 'Closing...' : 'Close Lobby') : (isLeaving ? 'Leaving...' : 'Leave Lobby')}
                </Button>
            </CardActions>
        </Card>
    );
}
