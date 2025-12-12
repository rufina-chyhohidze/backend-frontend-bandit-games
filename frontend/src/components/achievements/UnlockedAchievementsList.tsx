import { Box, Card, CardContent, Stack, Typography, Chip } from "@mui/material";
import EmojiEventsRoundedIcon from "@mui/icons-material/EmojiEventsRounded";
import type { UnlockedAchievement } from "../../models/unlockedAchievement";

export function UnlockedAchievementsList({ achievements }: { achievements: UnlockedAchievement[] }) {
    if (!achievements.length) {
        return (
            <Box sx={{ textAlign: "center", py: 2, color: "#d0d0e5" }}>
                <Typography>No unlocked achievements yet.</Typography>
            </Box>
        );
    }

    return (
        <Stack spacing={2}>
            {achievements.map((a, index) => (
                <Card
                    key={a.achievementId}
                    elevation={0}
                    sx={{
                        backgroundColor: "rgba(8, 12, 32, 0.99)",
                        borderRadius: 2,
                        boxShadow: "0 10px 26px rgba(0,0,0,0.45)",
                        "&:hover": {
                            transform: "translateY(-2px)",
                            boxShadow: "0 18px 38px rgba(124,140,255,0.16)",
                        },
                        transition: "all 0.2s ease",
                    }}
                >
                    <CardContent>
                        <Stack direction="row" justifyContent="space-between" gap={2}>
                            <Box>
                                <Typography
                                    variant="overline"
                                    sx={{ color: "#7cffc5", letterSpacing: 1, fontSize: 11 }}
                                >
                                    UNLOCKED #{index + 1}
                                </Typography>

                                <Typography variant="subtitle1" sx={{ color: "white", fontWeight: 700 }}>
                                    {a.name}
                                </Typography>

                                <Typography variant="body2" sx={{ color: "#cfd2ff", mt: 0.6 }}>
                                    {a.description}
                                </Typography>

                                {a.unlockHint && (
                                    <Typography variant="body2" sx={{ mt: 1.2, color: "#9dd0ff" }}>
                                        <strong>Hint:</strong> {a.unlockHint}
                                    </Typography>
                                )}
                            </Box>

                            <Chip
                                size="small"
                                icon={<EmojiEventsRoundedIcon />}
                                label="Unlocked"
                                sx={{
                                    bgcolor: "rgba(0, 220, 130, 0.18)",
                                    color: "#7cffc5",
                                    border: "1px solid rgba(124, 255, 197, 0.5)",
                                    "& .MuiChip-icon": { color: "#7cffc5" },
                                }}
                            />
                        </Stack>
                    </CardContent>
                </Card>
            ))}
        </Stack>
    );
}
