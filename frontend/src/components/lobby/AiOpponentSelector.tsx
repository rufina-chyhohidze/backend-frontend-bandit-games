import { Box, Stack, Typography, ToggleButtonGroup, ToggleButton, CircularProgress } from "@mui/material";
import SmartToyRoundedIcon from "@mui/icons-material/SmartToyRounded";
import type { LobbyDto } from "../../models/lobby";

interface AiOpponentSelectorProps {
    lobby: LobbyDto;
    isHost: boolean;
    onChooseAi: (difficulty: "EASY" | "MEDIUM" | "HARD" | "ML") => void;
    isChoosing: boolean;
}

export function AiOpponentSelector({ lobby, isHost, onChooseAi, isChoosing }: AiOpponentSelectorProps) {
    if (!isHost) return null;

    const hasHumanGuest = !!lobby.guestPlayerId;
    const isAiGuest =
        lobby.guestType === "AI_EASY" ||
        lobby.guestType === "AI_MEDIUM" ||
        lobby.guestType === "AI_HARD" ||
        lobby.guestType === "AI_ML";

    const currentDifficulty =
        lobby.guestType === "AI_EASY"
            ? "EASY"
            : lobby.guestType === "AI_MEDIUM"
                ? "MEDIUM"
                : lobby.guestType === "AI_HARD"
                    ? "HARD"
                    : lobby.guestType === "AI_ML"
                        ? "ML"
                        : null;

    return (
        <Box
            sx={{
                bgcolor: "rgba(0, 0, 0, 0.3)",
                borderRadius: 2,
                p: 2,
                border: "1px solid rgba(255, 255, 255, 0.1)",
            }}
        >
            <Stack spacing={1.5}>
                <Stack direction="row" alignItems="center" spacing={1}>
                    <SmartToyRoundedIcon sx={{ fontSize: 20, color: "#fbbf24" }} />
                    <Typography variant="subtitle2" sx={{ color: "#fff", fontWeight: 600 }}>
                        AI Opponent
                    </Typography>
                </Stack>

                {hasHumanGuest && !isAiGuest ? (
                    <Typography variant="body2" sx={{ color: "#d0d0e5" }}>
                        A human guest has joined this lobby. Remove them first if you want to play
                        against an AI.
                    </Typography>
                ) : (
                    <>
                        <Typography variant="body2" sx={{ color: "#d0d0e5" }}>
                            Choose an AI difficulty as your opponent.
                        </Typography>

                        <ToggleButtonGroup
                            exclusive
                            value={currentDifficulty}
                            onChange={(_, value: "EASY" | "MEDIUM" | "HARD" | "ML" | null) => {
                                if (!value || isChoosing) return;
                                onChooseAi(value);
                            }}
                            sx={{
                                "& .MuiToggleButton-root": {
                                    textTransform: "none",
                                    color: "#e5e7eb",
                                    borderColor: "rgba(255,255,255,0.2)",
                                    "&.Mui-selected": {
                                        bgcolor: "rgba(251, 191, 36, 0.2)",
                                        borderColor: "#fbbf24",
                                        color: "#fbbf24",
                                    },
                                },
                            }}
                            size="small"
                        >
                            <ToggleButton value="EASY">Easy</ToggleButton>
                            <ToggleButton value="MEDIUM">Medium</ToggleButton>
                            <ToggleButton value="HARD">Hard</ToggleButton>
                            <ToggleButton value="ML">ML</ToggleButton>
                        </ToggleButtonGroup>

                        {isChoosing && (
                            <Stack direction="row" spacing={1} alignItems="center">
                                <CircularProgress size={16} sx={{ color: "#fbbf24" }} />
                                <Typography variant="caption" sx={{ color: "#e5e7eb" }}>
                                    Applying AI settings...
                                </Typography>
                            </Stack>
                        )}
                    </>
                )}
            </Stack>
        </Box>
    );
}
