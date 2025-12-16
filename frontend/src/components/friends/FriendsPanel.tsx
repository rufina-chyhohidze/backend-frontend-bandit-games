import { Box, Chip, CircularProgress, Divider, Stack, Typography, Button, IconButton } from "@mui/material";
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";
import PersonRemoveRoundedIcon from "@mui/icons-material/PersonRemoveRounded";
import type { PlayerDtoWithName } from "../../models/friendship";

export function FriendsPanel(props: {
    friends: PlayerDtoWithName[];
    loading: boolean;
    onInviteToLobby: (id: string) => void;
    onRemoveFriend: (id: string) => void;
    invitePending: boolean;
    removePending: boolean;
}) {
    const panelSx = {
        bgcolor: "rgba(255,255,255,0.035)",
        border: "1px solid rgba(255,255,255,0.08)",
        borderRadius: 3,
        p: 1.25,
    };

    const rowSx = {
        p: 1.1,
        borderRadius: 2.5,
        bgcolor: "rgba(255,255,255,0.045)",
        border: "1px solid rgba(255,255,255,0.08)",
    };

    const pillBtnSx = {
        borderRadius: 999,
        textTransform: "none" as const,
        px: 1.75,
        py: 0.65,
        whiteSpace: "nowrap" as const,
    };

    return (
        <Box sx={panelSx}>
            <Stack direction="row" alignItems="center" justifyContent="space-between">
                <Typography sx={{ color: "#fff", fontWeight: 800 }}>Your Friends</Typography>
                <Chip
                    label={props.friends.length}
                    size="small"
                    sx={{ bgcolor: "rgba(255,255,255,0.10)", color: "rgba(255,255,255,0.85)" }}
                />
            </Stack>

            <Divider sx={{ borderColor: "rgba(255,255,255,0.08)", my: 1.25 }} />

            {props.loading ? (
                <Stack alignItems="center" py={3}>
                    <CircularProgress sx={{ color: "#fff" }} />
                </Stack>
            ) : props.friends.length ? (
                <Stack spacing={1}>
                    {props.friends.map((f) => (
                        <Box key={f.playerId} sx={rowSx}>
                            <Stack direction="row" alignItems="center" justifyContent="space-between" spacing={1.5}>
                                <Typography sx={{ color: "#fff", fontWeight: 800 }} noWrap>
                                    {f.username}
                                </Typography>

                                <Stack direction="row" spacing={1} sx={{ flexShrink: 0 }}>
                                    <Button
                                        variant="contained"
                                        size="small"
                                        startIcon={<SportsEsportsRoundedIcon />}
                                        onClick={() => props.onInviteToLobby(f.playerId)}
                                        disabled={props.invitePending}
                                        sx={{
                                            ...pillBtnSx,
                                            bgcolor: "#2f7dff",
                                            "&:hover": { bgcolor: "#1f62cf" },
                                        }}
                                    >
                                        Invite
                                    </Button>

                                    <IconButton
                                        size="small"
                                        onClick={() => props.onRemoveFriend(f.playerId)}
                                        disabled={props.removePending}
                                        sx={{
                                            color: "#ff8b8b",
                                            bgcolor: "rgba(255,107,107,0.10)",
                                            border: "1px solid rgba(255,107,107,0.25)",
                                            "&:hover": { bgcolor: "rgba(255,107,107,0.16)" },
                                            borderRadius: 2,
                                        }}
                                    >
                                        <PersonRemoveRoundedIcon fontSize="small" />
                                    </IconButton>
                                </Stack>
                            </Stack>
                        </Box>
                    ))}
                </Stack>
            ) : (
                <Typography sx={{ color: "rgba(255,255,255,0.72)" }}>
                    No friends yet. Use search to add some!
                </Typography>
            )}
        </Box>
    );
}
