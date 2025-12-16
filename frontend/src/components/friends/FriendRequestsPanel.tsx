import { Box, Chip, CircularProgress, Divider, IconButton, Stack, Typography } from "@mui/material";
import CheckRoundedIcon from "@mui/icons-material/CheckRounded";
import CloseRoundedIcon from "@mui/icons-material/CloseRounded";
import type { PlayerDtoWithName } from "../../models/friendship";

export function FriendRequestsPanel(props: {
    loading: boolean;
    requests: PlayerDtoWithName[];
    onAccept: (id: string) => void;
    onReject: (id: string) => void;
    acceptPending: boolean;
    rejectPending: boolean;
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

    return (
        <Box sx={panelSx}>
            <Stack direction="row" alignItems="center" justifyContent="space-between">
                <Typography sx={{ color: "#fff", fontWeight: 800 }}>Friend Requests</Typography>
                <Chip
                    label={props.requests.length}
                    size="small"
                    sx={{ bgcolor: "rgba(255,193,7,0.16)", color: "#ffd36a" }}
                />
            </Stack>

            <Divider sx={{ borderColor: "rgba(255,255,255,0.08)", my: 1.1 }} />

            {props.loading ? (
                <Stack alignItems="center" py={2.25}>
                    <CircularProgress sx={{ color: "#fff" }} />
                </Stack>
            ) : props.requests.length ? (
                <Stack spacing={1}>
                    {props.requests.map((req) => (
                        <Box key={req.playerId} sx={rowSx}>
                            <Stack direction="row" alignItems="center" justifyContent="space-between" spacing={1.5}>
                                <Typography sx={{ color: "#fff", fontWeight: 800 }} noWrap>
                                    {req.username}
                                </Typography>

                                <Stack direction="row" spacing={1} sx={{ flexShrink: 0 }}>
                                    <IconButton
                                        size="small"
                                        onClick={() => props.onAccept(req.playerId)}
                                        disabled={props.acceptPending}
                                        sx={{
                                            color: "#22c55e",
                                            bgcolor: "rgba(34,197,94,0.10)",
                                            border: "1px solid rgba(34,197,94,0.25)",
                                            "&:hover": { bgcolor: "rgba(34,197,94,0.16)" },
                                        }}
                                    >
                                        <CheckRoundedIcon fontSize="small" />
                                    </IconButton>

                                    <IconButton
                                        size="small"
                                        onClick={() => props.onReject(req.playerId)}
                                        disabled={props.rejectPending}
                                        sx={{
                                            color: "#ff8b8b",
                                            bgcolor: "rgba(255,107,107,0.10)",
                                            border: "1px solid rgba(255,107,107,0.25)",
                                            "&:hover": { bgcolor: "rgba(255,107,107,0.16)" },
                                        }}
                                    >
                                        <CloseRoundedIcon fontSize="small" />
                                    </IconButton>
                                </Stack>
                            </Stack>
                        </Box>
                    ))}
                </Stack>
            ) : (
                <Typography sx={{ color: "rgba(255,255,255,0.72)" }}>No pending friend requests.</Typography>
            )}
        </Box>
    );
}
