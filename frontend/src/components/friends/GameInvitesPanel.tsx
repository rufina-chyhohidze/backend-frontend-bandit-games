import { Box, Chip, CircularProgress, Divider, IconButton, Stack, Typography } from "@mui/material";
import CheckRoundedIcon from "@mui/icons-material/CheckRounded";
import CloseRoundedIcon from "@mui/icons-material/CloseRounded";

export function GameInvitesPanel(props: {
    loading: boolean;
    invites: any[];
    guessFromName: (fromId: string) => string;
    normalizeInvitationId: (inv: any) => string;
    normalizeFromPlayerId: (inv: any) => string;
    normalizeLobbyId: (inv: any) => string;
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
                <Typography sx={{ color: "#fff", fontWeight: 800 }}>Game Invites</Typography>
                <Chip
                    label={props.invites.length}
                    size="small"
                    sx={{ bgcolor: "rgba(0,123,255,0.16)", color: "#8bc2ff" }}
                />
            </Stack>

            <Divider sx={{ borderColor: "rgba(255,255,255,0.08)", my: 1.1 }} />

            {props.loading ? (
                <Stack alignItems="center" py={2.25}>
                    <CircularProgress sx={{ color: "#fff" }} />
                </Stack>
            ) : props.invites.length ? (
                <Stack spacing={1}>
                    {props.invites.map((inv) => {
                        const invitationId = props.normalizeInvitationId(inv);
                        const fromId = props.normalizeFromPlayerId(inv);
                        const lobbyId = props.normalizeLobbyId(inv);
                        const fromName = props.guessFromName(fromId);

                        return (
                            <Box key={invitationId} sx={rowSx}>
                                <Stack direction="row" alignItems="center" justifyContent="space-between" spacing={1.5}>
                                    <Box sx={{ minWidth: 0 }}>
                                        <Typography sx={{ color: "#fff", fontWeight: 800 }} noWrap>
                                            {fromName}
                                        </Typography>
                                        <Typography variant="caption" sx={{ color: "rgba(255,255,255,0.70)" }} noWrap>
                                            Lobby: {lobbyId}
                                        </Typography>
                                    </Box>

                                    <Stack direction="row" spacing={1} sx={{ flexShrink: 0 }}>
                                        <IconButton
                                            size="small"
                                            onClick={() => props.onAccept(invitationId)}
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
                                            onClick={() => props.onReject(invitationId)}
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
                        );
                    })}
                </Stack>
            ) : (
                <Typography sx={{ color: "rgba(255,255,255,0.72)" }}>No pending game invites.</Typography>
            )}
        </Box>
    );
}
