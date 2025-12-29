import {
    Box,
    Chip,
    CircularProgress,
    Divider,
    Stack,
    Typography,
    Button,
    IconButton,
} from "@mui/material";
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
        p: 1,
        maxHeight: 340,
        display: "flex",
        flexDirection: "column" as const,
    };

    const rowSx = {
        p: 0.9,
        borderRadius: 2.5,
        bgcolor: "rgba(255,255,255,0.045)",
        border: "1px solid rgba(255,255,255,0.08)",
    };

    const pillBtnSx = {
        borderRadius: 999,
        textTransform: "none" as const,
        px: 1.5,
        py: 0.55,
        whiteSpace: "nowrap" as const,
        fontSize: 13,
    };

    return (
        <Box sx={panelSx}>
            <Stack
                direction="row"
                alignItems="center"
                justifyContent="space-between"
            >
                <Typography sx={{ color: "#fff", fontWeight: 800, fontSize: 14 }}>
                    Your Friends
                </Typography>
                <Chip
                    label={props.friends.length}
                    size="small"
                    sx={{
                        bgcolor: "rgba(255,255,255,0.10)",
                        color: "rgba(255,255,255,0.85)",
                        fontSize: 11,
                        height: 22,
                    }}
                />
            </Stack>

            <Divider
                sx={{ borderColor: "rgba(255,255,255,0.08)", my: 1 }}
            />

            {props.loading ? (
                <Stack alignItems="center" py={2} sx={{ flexGrow: 1 }}>
                    <CircularProgress sx={{ color: "#fff" }} size={22} />
                </Stack>
            ) : props.friends.length ? (
                <Stack
                    spacing={0.8}
                    sx={{
                        flexGrow: 1,
                        overflowY: "auto",
                        pr: 0.5,
                    }}
                >
                    {props.friends.map((f) => (
                        <Box key={f.playerId} sx={rowSx}>
                            <Stack
                                direction="row"
                                alignItems="center"
                                justifyContent="space-between"
                                spacing={1}
                            >
                                <Typography
                                    sx={{ color: "#fff", fontWeight: 700, fontSize: 13 }}
                                    noWrap
                                >
                                    {f.username}
                                </Typography>

                                <Stack direction="row" spacing={0.75} sx={{ flexShrink: 0 }}>
                                    <Button
                                        variant="contained"
                                        size="small"
                                        startIcon={
                                            <SportsEsportsRoundedIcon sx={{ fontSize: 16 }} />
                                        }
                                        onClick={() =>
                                            props.onInviteToLobby(f.playerId)
                                        }
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
                                        onClick={() =>
                                            props.onRemoveFriend(f.playerId)
                                        }
                                        disabled={props.removePending}
                                        sx={{
                                            color: "#ff8b8b",
                                            bgcolor: "rgba(255,107,107,0.10)",
                                            border: "1px solid rgba(255,107,107,0.25)",
                                            "&:hover": {
                                                bgcolor: "rgba(255,107,107,0.16)",
                                            },
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
                <Stack sx={{ flexGrow: 1 }} justifyContent="center">
                    <Typography
                        sx={{
                            color: "rgba(255,255,255,0.72)",
                            fontSize: 13,
                        }}
                    >
                        No friends yet. Use search to add some!
                    </Typography>
                </Stack>
            )}
        </Box>
    );
}
