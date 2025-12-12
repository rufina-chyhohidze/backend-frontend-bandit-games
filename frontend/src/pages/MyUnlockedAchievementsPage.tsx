import { useNavigate, useParams } from "react-router-dom";
import { Box, Typography, CircularProgress, Alert, Button, Stack, Divider, Chip } from "@mui/material";
import ArrowBackRoundedIcon from "@mui/icons-material/ArrowBackRounded";
import EmojiEventsRoundedIcon from "@mui/icons-material/EmojiEventsRounded";

import { useMyUnlockedAchievements } from "../hooks/useUnlockedAchievements";
import { UnlockedAchievementsList } from "../components/achievements/UnlockedAchievementsList";
import { useGames } from "../hooks/useGames";

export function MyUnlockedAchievementsPage() {
  const { gameId } = useParams<{ gameId: string }>();
  const navigate = useNavigate();

  const { games } = useGames();
  const game = games?.find((g) => g.gameId === gameId) ?? null;

  const { data, isLoading, isError, error } = useMyUnlockedAchievements(gameId);

  if (!gameId) return <Alert severity="error">Missing game id.</Alert>;

  return (
    <Box
      sx={{
        minHeight: "100vh",
        width: "100%",
        background: "linear-gradient(135deg, #141e30 0%, #243b55 100%)",
        display: "flex",
        justifyContent: "center",
        pt: { xs: 9, sm: 10 },
        pb: 4,
      }}
    >
      <Box
        sx={{
          width: { xs: "97vw", sm: 650, md: 850 },
          maxWidth: "97vw",
          borderRadius: 3,
          p: { xs: 3, md: 4 },
          bgcolor: "rgba(7, 11, 25, 0.98)",
          boxShadow: "0 24px 55px rgba(0,0,0,0.85)",
          color: "white",
        }}
      >
        <Stack spacing={2.5}>
          <Stack direction="row" alignItems="center" justifyContent="space-between" gap={2}>
            <Stack direction="row" alignItems="center" spacing={1.5}>
              <EmojiEventsRoundedIcon sx={{ fontSize: 32, color: "#7cffc5" }} />
              <Box>
                <Typography variant="h5" sx={{ fontWeight: 800 }}>
                  My Unlocked Achievements
                </Typography>
                <Typography variant="body2" sx={{ color: "#c3c6ff", mt: 0.3 }}>
                  {game ? `For ${game.name}` : "Per game"}
                </Typography>
              </Box>
            </Stack>

            <Button
              variant="outlined"
              onClick={() => navigate(`/games/${gameId}/achievements`)}
              startIcon={<ArrowBackRoundedIcon />}
              sx={{
                borderColor: "rgba(255,255,255,0.6)",
                color: "white",
                textTransform: "none",
                borderRadius: 999,
                "&:hover": { borderColor: "#fff", backgroundColor: "rgba(255,255,255,0.10)" },
              }}
            >
              Back
            </Button>
          </Stack>

          <Divider sx={{ borderColor: "rgba(255,255,255,0.11)" }} />

          {isLoading && (
            <Box display="flex" justifyContent="center" py={4}>
              <CircularProgress sx={{ color: "white" }} />
            </Box>
          )}

          {isError && (
            <Alert severity="error">
              {(error as any)?.message ?? "Failed to load unlocked achievements"}
            </Alert>
          )}

          {!isLoading && !isError && (
            <>
              <Chip
                icon={<EmojiEventsRoundedIcon />}
                label={`${data?.length ?? 0} unlocked`}
                sx={{
                  alignSelf: "flex-start",
                  bgcolor: "rgba(0, 220, 130, 0.18)",
                  color: "#7cffc5",
                  border: "1px solid rgba(124, 255, 197, 0.4)",
                  "& .MuiChip-icon": { color: "#7cffc5" },
                }}
              />
              <UnlockedAchievementsList achievements={data ?? []} />
            </>
          )}
        </Stack>
      </Box>
    </Box>
  );
}
