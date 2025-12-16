import { Box, Chip, Divider, Stack, Typography } from "@mui/material";
import HourglassEmptyRoundedIcon from "@mui/icons-material/HourglassEmptyRounded";
import type { PlayerDtoWithName } from "../../models/friendship";

export function SentRequestsPanel({ sentRequests }: { sentRequests: PlayerDtoWithName[] }) {
    const panelSx = {
        bgcolor: "rgba(255,255,255,0.035)",
        border: "1px solid rgba(255,255,255,0.08)",
        borderRadius: 3,
        p: 1.25,
    };

    return (
        <Box sx={panelSx}>
            <Stack direction="row" alignItems="center" justifyContent="space-between">
                <Typography sx={{ color: "#fff", fontWeight: 800 }}>Sent</Typography>
                <Chip
                    label={sentRequests.length}
                    size="small"
                    sx={{ bgcolor: "rgba(100,149,237,0.16)", color: "#a9c6ff" }}
                />
            </Stack>

            <Divider sx={{ borderColor: "rgba(255,255,255,0.08)", my: 1.1 }} />

            {sentRequests.length ? (
                <Box sx={{ display: "flex", flexWrap: "wrap", gap: 1 }}>
                    {sentRequests.map((s) => (
                        <Chip
                            key={s.playerId}
                            icon={<HourglassEmptyRoundedIcon />}
                            label={s.username}
                            size="small"
                            sx={{
                                bgcolor: "rgba(100,149,237,0.12)",
                                color: "#d6e4ff",
                                border: "1px solid rgba(100,149,237,0.25)",
                            }}
                        />
                    ))}
                </Box>
            ) : (
                <Typography sx={{ color: "rgba(255,255,255,0.72)" }}>No sent requests.</Typography>
            )}
        </Box>
    );
}
