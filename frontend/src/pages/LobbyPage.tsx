import { Box, CircularProgress, Typography, Stack, Button, Grid } from "@mui/material";
import HubRoundedIcon from "@mui/icons-material/HubRounded";
import PlayArrowRoundedIcon from "@mui/icons-material/PlayArrowRounded";
import { NoLobbyState } from "../components/lobby/NoLobbyState";
import { LobbyCard } from "../components/lobby/LobbyCard";
import {
    useLobby,
    useAddPlayerToLobby,
    useChooseGame,
    useStartGame,
    useOpenLobbies,
} from "../hooks/useLobby";
import { useContext } from "react";
import SecurityContext from "../context/SecurityContext";
import { OpenLobbiesList } from "../components/lobby/OpenLobbyList";
import { GameSelector } from "../components/lobby/GameSelector";
import { TopNavBar } from "../components/layout/TopNavBar";

export function LobbyPage() {
    const { isAuthenticated, loggedInUser, login } = useContext(SecurityContext);
    const { lobby, isLoading, isError, refreshLobby } = useLobby();
    const { openLobbies, isLoadingLobbies, isLobbiesError, refreshOpenLobbies } = useOpenLobbies();

    const addPlayerMutation = useAddPlayerToLobby();
    const chooseGameMutation = useChooseGame();
    const startGameMutation = useStartGame(loggedInUser!, lobby!);

    const handleJoinLobby = (lobbyId: string) => {
        if (!loggedInUser) return;

        const playerId = loggedInUser.id ?? loggedInUser.name;

        addPlayerMutation.mutate(
            { lobbyId, playerId },
            {
                onSuccess: () => {
                    console.log(`Successfully joined lobby: ${lobbyId}`);
                    refreshLobby();
                    refreshOpenLobbies();
                },
                onError: (error) => {
                    console.error(`Failed to join lobby: ${lobbyId}`, error);
                },
            }
        );
    };

    const handleSelectGame = (gameId: string) => {
        if (
            !lobby ||
            !loggedInUser ||
            lobby.hostPlayerId !== (loggedInUser.id ?? loggedInUser.name)
        ) {
            console.warn("Cannot select game: Not in a lobby or not the host.");
            return;
        }

        chooseGameMutation.mutate(
            { lobbyId: lobby.lobbyId, gameId },
            {
                onSuccess: () => {
                    console.log(`Successfully set game to ${gameId}`);
                },
                onError: (error) => {
                    console.error("Failed to select game:", error);
                },
            }
        );
    };

    const handleStartGame = () => {
        if (
            !lobby ||
            !loggedInUser ||
            lobby.hostPlayerId !== (loggedInUser.id ?? loggedInUser.name)
        ) {
            console.warn("Cannot start game: Not in a lobby or not the host.");
            return;
        }
        if (!lobby.guestPlayerId || !lobby.gameId) {
            console.warn("Cannot start game: Waiting for guest or game selection.");
            return;
        }

        startGameMutation.mutate(lobby.lobbyId);
    };

    const handleJoinGame = () => {
        if (!lobby || !loggedInUser) {
            console.warn("Cannot join game: Not in a lobby.");
            return;
        }

        const currentUserId = loggedInUser.id ?? loggedInUser.name;
        if (lobby.hostPlayerId === currentUserId) {
            console.warn("Host should use Start Game button instead.");
            return;
        }

        if (!lobby.gameId) {
            console.warn("Cannot join game: No game selected yet.");
            return;
        }

        startGameMutation.mutate(lobby.lobbyId);
    };

    // --- Authentication Guard ---
    if (!isAuthenticated()) {
        return (
            <>
                <TopNavBar />
                <Box
                    className="scrollable-container"
                    sx={{
                        minHeight: "calc(100vh - 64px)",
                        width: "100%",
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                        background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                        color: "#fff",
                        textAlign: "center",
                        p: 4,
                    }}
                >
                    <HubRoundedIcon sx={{ fontSize: 48, color: "#9d7dff", mb: 2 }} />
                    <Typography variant="h5" gutterBottom sx={{ color: "#fff", fontWeight: 600 }}>
                        Access Denied
                    </Typography>
                    <Typography variant="body1" sx={{ color: "#d0d0e5", mb: 3 }}>
                        You must be logged in to view the Game Lobby.
                    </Typography>
                    <Button
                        variant="contained"
                        onClick={login}
                        sx={{
                            bgcolor: "#9d7dff",
                            "&:hover": { bgcolor: "#7f5aff" },
                            color: "white",
                            textTransform: "none",
                            borderRadius: 999,
                            px: 4,
                            py: 1.5,
                        }}
                    >
                        Log In to Continue
                    </Button>
                </Box>
            </>
        );
    }

    // --- User Profile Loading Guard ---
    if (!loggedInUser) {
        return (
            <>
                <TopNavBar />
                <Box
                    className="scrollable-container"
                    sx={{
                        minHeight: "calc(100vh - 64px)",
                        width: "100%",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                    }}
                >
                    <CircularProgress sx={{ color: "#fff" }} />
                    <Typography variant="body1" sx={{ color: "#d0d0e5", ml: 2 }}>
                        Loading user profile...
                    </Typography>
                </Box>
            </>
        );
    }

    const currentUserId: string = loggedInUser.id ?? loggedInUser.name;

    const loading = isLoading;
    const hasLobby = !isLoading && !isError && !!lobby;
    const showNoLobby = !isLoading && !isError && !lobby;

    const isHost = hasLobby && lobby && lobby.hostPlayerId === currentUserId;
    const isGuest = hasLobby && lobby && lobby.guestPlayerId === currentUserId;
    const isLobbyReady = hasLobby && lobby && !!lobby.guestPlayerId && !!lobby.gameId;
    const isStarting = startGameMutation.isPending;

    return (
        <>
            <TopNavBar />
            <Box
                className="scrollable-container"
                sx={{
                    minHeight: "calc(100vh - 64px)",
                    width: "100%",
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                    py: 4,
                }}
            >
                <Box
                    sx={{
                        width: { xs: "100%", sm: 900, md: 1200 },
                        maxWidth: "98%",
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

                    <Grid container spacing={4} sx={{ position: "relative", zIndex: 1 }}>
                        {/* LEFT/CENTER COLUMN */}
                        <Grid size={{ xs: 12, md: 7, lg: 8 }}>
                            <Stack spacing={3} alignItems="center" sx={{ textAlign: "center" }}>
                                <Stack
                                    direction="row"
                                    alignItems="center"
                                    spacing={1}
                                    sx={{ mb: 2 }}
                                >
                                    <HubRoundedIcon sx={{ fontSize: 32, color: "#9d7dff" }} />
                                    <Typography
                                        variant="h4"
                                        component="h1"
                                        sx={{
                                            fontWeight: 700,
                                            letterSpacing: 0.5,
                                            color: "#fff",
                                        }}
                                    >
                                        Your Game Status
                                    </Typography>
                                </Stack>

                                <Typography
                                    variant="body1"
                                    sx={{ color: "#d0d0e5", maxWidth: 600 }}
                                >
                                    Create a lobby or join one from the list on the right.
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
                                            <Typography
                                                variant="body1"
                                                sx={{ color: "#d0d0e5" }}
                                            >
                                                Checking for active lobby...
                                            </Typography>
                                        </Stack>
                                    )}

                                    {isError && (
                                        <Typography color="error" variant="body1" py={5}>
                                            Error loading your lobby.
                                        </Typography>
                                    )}

                                    {showNoLobby && (
                                        <NoLobbyState onLobbyCreated={refreshLobby} />
                                    )}

                                    {hasLobby && lobby && (
                                        <Stack spacing={3} sx={{ width: "100%" }}>
                                            <LobbyCard
                                                lobby={lobby}
                                                currentUserId={currentUserId}
                                            />

                                            <GameSelector
                                                currentGameId={lobby.gameId || null}
                                                isHost={isHost}
                                                onSelectGame={handleSelectGame}
                                                isSelecting={chooseGameMutation.isPending}
                                            />

                                            {isHost && (
                                                <Button
                                                    variant="contained"
                                                    fullWidth
                                                    onClick={handleStartGame}
                                                    disabled={!isLobbyReady || isStarting}
                                                    startIcon={
                                                        isStarting ? (
                                                            <CircularProgress
                                                                size={20}
                                                                color="inherit"
                                                            />
                                                        ) : (
                                                            <PlayArrowRoundedIcon />
                                                        )
                                                    }
                                                    sx={{
                                                        bgcolor: isLobbyReady
                                                            ? "#28a745"
                                                            : "#6c757d",
                                                        "&:hover": {
                                                            bgcolor: isLobbyReady
                                                                ? "#218838"
                                                                : "#5a6268",
                                                        },
                                                        textTransform: "none",
                                                        fontWeight: 600,
                                                        p: 1.5,
                                                        transition:
                                                            "background-color 0.2s",
                                                    }}
                                                >
                                                    {isStarting
                                                        ? "Launching Game..."
                                                        : !lobby.guestPlayerId
                                                            ? "Waiting for Guest..."
                                                            : !lobby.gameId
                                                                ? "Select Game to Start"
                                                                : "Start Game!"}
                                                </Button>
                                            )}

                                            {isGuest && (
                                                <Button
                                                    variant="contained"
                                                    fullWidth
                                                    onClick={handleJoinGame}
                                                    disabled={!lobby.gameId || isStarting}
                                                    startIcon={
                                                        isStarting ? (
                                                            <CircularProgress
                                                                size={20}
                                                                color="inherit"
                                                            />
                                                        ) : (
                                                            <PlayArrowRoundedIcon />
                                                        )
                                                    }
                                                    sx={{
                                                        bgcolor: lobby.gameId
                                                            ? "#007bff"
                                                            : "#6c757d",
                                                        "&:hover": {
                                                            bgcolor: lobby.gameId
                                                                ? "#0056b3"
                                                                : "#5a6268",
                                                        },
                                                        textTransform: "none",
                                                        fontWeight: 600,
                                                        p: 1.5,
                                                        transition:
                                                            "background-color 0.2s",
                                                    }}
                                                >
                                                    {isStarting
                                                        ? "Joining Game..."
                                                        : !lobby.gameId
                                                            ? "Waiting for Host to Select Game..."
                                                            : "Join Game!"}
                                                </Button>
                                            )}
                                        </Stack>
                                    )}
                                </Box>
                            </Stack>
                        </Grid>

                        <Grid size={{ xs: 12, md: 5, lg: 4 }}>
                            <OpenLobbiesList
                                openLobbies={openLobbies}
                                isLoading={isLoadingLobbies}
                                isError={isLobbiesError}
                                onJoinLobby={handleJoinLobby}
                            />
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </>
    );
}
