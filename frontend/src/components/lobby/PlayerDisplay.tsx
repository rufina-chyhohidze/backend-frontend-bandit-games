import { Stack, Avatar, Typography, Chip } from "@mui/material";
import PersonIcon from "@mui/icons-material/PersonRounded";

interface PlayerDisplayProps {
    playerId?: string | null;
    playerType?: string | null;
    isHost?: boolean;
    isYou?: boolean;
}

export function PlayerDisplay({ playerId, playerType, isHost, isYou }: PlayerDisplayProps) {
    const isAi =
        !!playerType &&
        (playerType === "AI_EASY" || playerType === "AI_MEDIUM" || playerType === "AI_HARD");

    // AI opponent slot
    if (!playerId && isAi) {
        const label =
            playerType === "AI_EASY"
                ? "AI (Easy)"
                : playerType === "AI_MEDIUM"
                    ? "AI (Medium)"
                    : "AI (Hard)";

        return (
            <Stack alignItems="center" spacing={1} sx={{ width: 100 }}>
                <Avatar sx={{ bgcolor: "#f59e0b", width: 60, height: 60, fontSize: "1.5rem" }}>
                    AI
                </Avatar>
                <Typography variant="body1" sx={{ color: "#fff" }}>
                    {label}
                </Typography>
                <Chip
                    label="AI Opponent"
                    size="small"
                    sx={{ bgcolor: "rgba(245, 158, 11, 0.15)", color: "#fbbf24" }}
                />
            </Stack>
        );
    }

    if (!playerId) {
        return (
            <Stack alignItems="center" spacing={1} sx={{ width: 100, opacity: 0.5 }}>
                <Avatar sx={{ bgcolor: "#333", width: 60, height: 60 }}>
                    <PersonIcon sx={{ fontSize: 40, color: "#999" }} />
                </Avatar>
                <Typography variant="body1" sx={{ color: "#aaa" }}>
                    Waiting...
                </Typography>
                <Chip
                    label="Empty Slot"
                    size="small"
                    sx={{ bgcolor: "rgba(255,255,255,0.1)", color: "#aaa" }}
                />
            </Stack>
        );
    }

    return (
        <Stack alignItems="center" spacing={1} sx={{ width: 100 }}>
            <Avatar sx={{ bgcolor: isYou ? "#00dccc" : "#9d7dff", width: 60, height: 60, fontSize: "1.5rem" }}>
                {playerId[1]}
            </Avatar>
            <Typography variant="body1" fontWeight={isYou ? 700 : 500} sx={{ color: "#fff" }}>
                {isYou ? "You" : playerId}
            </Typography>
            <Chip label={isHost ? "Host" : "Guest"} size="small" sx={{ fontSize: "0.7rem" }} />
            {playerType && (
                <Typography variant="caption" sx={{ color: "#999", fontSize: "0.6rem" }}>
                    {playerType}
                </Typography>
            )}
        </Stack>
    );
}
