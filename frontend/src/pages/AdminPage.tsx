import { useEffect, useState } from "react";
import { Box, Typography, CircularProgress } from "@mui/material";
import { fetchPendingGames, approveGame, rejectGame } from "../services/adminGameService";
import type { AdminGame } from "../models/adminGame";
import { AdminGameCard } from "../components/admin/AdminGameCard";

export function AdminPage() {
    const [games, setGames] = useState<AdminGame[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    async function loadGames() {
        setLoading(true);
        try {
            const pending = await fetchPendingGames();
            setGames(pending);
        } catch (e) {
            console.error("Failed to load pending games", e);
        } finally {
            setLoading(false);
        }
    }

    async function handleApprove(gameId: string) {
        try {
            await approveGame(gameId);
            await loadGames();
        } catch (e) {
            console.error("Failed to approve game", e);
        }
    }

    async function handleReject(gameId: string) {
        try {
            await rejectGame(gameId);
            await loadGames();
        } catch (e) {
            console.error("Failed to reject game", e);
        }
    }

    useEffect(() => {
        loadGames();
    }, []);

    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100vw",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                position: "relative",
                overflow: "hidden",
            }}
        >
            <Box
                sx={{
                    position: "absolute",
                    top: 0,
                    left: 0,
                    right: 0,
                    zIndex: 2,
                }}
            >
            </Box>

            <Box
                sx={{
                    width: { xs: "96vw", sm: 700, md: 900 },
                    maxWidth: "96vw",
                    borderRadius: 4,
                    p: { xs: 2.5, md: 4 },
                    bgcolor: "rgba(10, 16, 36, 0.97)",
                    boxShadow: "0 24px 60px rgba(0,0,0,0.7)",
                    position: "relative",
                }}
            >
                <Box
                    sx={{
                        position: "absolute",
                        inset: 0,
                        pointerEvents: "none",
                        opacity: 0.4,
                        background:
                            "radial-gradient(circle at 0% 0%, rgba(0,200,255,0.18), transparent 55%)," +
                            "radial-gradient(circle at 100% 100%, rgba(255,0,128,0.23), transparent 55%)",
                        zIndex: 0,
                        borderRadius: 4,
                    }}
                />

                <Box sx={{ position: "relative", zIndex: 1, color: "#fff" }}>
                    <Typography
                        variant="h4"
                        sx={{
                            fontWeight: 700,
                            letterSpacing: 0.5,
                            mb: 3,
                            color: "#7bb7ff",
                        }}
                    >
                        Game approval requests
                    </Typography>

                    <Box
                        sx={{
                            mt: 2,
                            p: 3,
                            borderRadius: 2,
                            bgcolor: "rgba(255,255,255,0.07)",
                            border: "1px solid rgba(255,255,255,0.15)",
                            color: "#cfd7ff",
                            boxShadow: "0 18px 40px rgba(0,0,0,0.5)",
                            mb: 3,
                        }}
                    >
                        <Typography variant="body1">
                            This is the admin area where you review and approve new games added to the platform.
                        </Typography>
                        <Typography variant="body2" sx={{ mt: 1.5, color: "#9db3ff" }}>
                            Below you’ll see all pending games. Approve to publish them or reject to keep them off the platform.
                        </Typography>
                    </Box>

                    {loading && (
                        <Box sx={{ display: "flex", justifyContent: "center", py: 4 }}>
                            <CircularProgress sx={{ color: "#7bb7ff" }} />
                        </Box>
                    )}

                    {!loading &&
                        games.map((game) => (
                            <AdminGameCard
                                key={game.gameId}
                                game={game}
                                onApprove={() => handleApprove(game.gameId)}
                                onReject={() => handleReject(game.gameId)}
                            />
                        ))}

                    {!loading && games.length === 0 && (
                        <Typography
                            variant="body2"
                            sx={{ mt: 2, color: "#9db3ff", fontStyle: "italic" }}
                        >
                            No pending games right now. 🎮
                        </Typography>
                    )}
                </Box>
            </Box>
        </Box>
    );
}

export default AdminPage;
