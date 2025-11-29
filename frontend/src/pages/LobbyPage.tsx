import { Box, CircularProgress, Typography, Stack } from "@mui/material";
import HubRoundedIcon from "@mui/icons-material/HubRounded";
import { NoLobbyState } from "../components/lobby/NoLobbyState";
import { LobbyCard } from "../components/lobby/LobbyCard";
import { useLobby } from "../hooks/useLobby";
import { useContext } from "react";
import SecurityContext from "../context/SecurityContext";

export function LobbyPage() {
    const { loggedInUser } = useContext(SecurityContext);

    if (!loggedInUser) {
        return <div>Loading user context...</div>;
    }

    const currentUserId: string = loggedInUser.id ?? loggedInUser.name;

    const { lobby, isLoading, isError, refreshLobby } = useLobby();

    const loading = isLoading;
    const hasLobby = !isLoading && !isError && !!lobby;
    const showNoLobby = !isLoading && !isError && !lobby;

    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100vw",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
            }}
        >
            <Box
                sx={{
                    width: { xs: "98vw", sm: 700, md: 900 },
                    maxWidth: "98vw",
                    borderRadius: 4,
                    p: { xs: 2, md: 4 },
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

                <Stack spacing={3} alignItems="center" sx={{ textAlign: "center", position: "relative", zIndex: 1 }}>
                    <Stack direction="row" alignItems="center" spacing={1} sx={{ mb: 2 }}>
                        <HubRoundedIcon sx={{ fontSize: 32, color: "#9d7dff" }} />
                        <Typography variant="h4" component="h1" sx={{ fontWeight: 700, letterSpacing: 0.5, color: "#fff" }}>
                            Game Lobby
                        </Typography>
                    </Stack>

                    <Typography variant="body1" sx={{ color: "#d0d0e5", maxWidth: 600 }}>
                        Your current status for hosting or joining a match.
                    </Typography>

                    <Box
                        sx={{
                            mt: 1,
                            p: { xs: 1, md: 2 },
                            width: "100%",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            minHeight: 250,
                        }}
                    >
                        {loading && (
                            <Stack alignItems="center" spacing={2} py={5}>
                                <CircularProgress sx={{ color: "#fff" }} />
                                <Typography variant="body1" sx={{ color: "#d0d0e5" }}>
                                    Checking for active lobby...
                                </Typography>
                            </Stack>
                        )}

                        {isError && (
                            <Typography color="error" variant="body1" py={5}>
                                Error loading lobby.
                            </Typography>
                        )}

                        {showNoLobby && (
                            <NoLobbyState
                                onLobbyCreated={refreshLobby}
                            />
                        )}

                        {hasLobby && lobby && <LobbyCard lobby={lobby} currentUserId={currentUserId} />}
                    </Box>
                </Stack>
            </Box>
        </Box>
    );
}