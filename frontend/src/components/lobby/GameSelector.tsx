// src/components/lobby/GameSelector.tsx

import { useState, useEffect } from "react";
import {
    Box,
    Typography,
    Stack,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
} from "@mui/material";
// Assuming you meant SportsEsportsRoundedIcon again, but HubRoundedIcon is fine too.
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import { fetchGames } from "../../services/gamesService.ts";
import type { Game } from "../../models/game";

interface GameSelectorProps {
    currentGameId: string | null;
    isHost: boolean;
    onSelectGame: (gameId: string) => void;
    isSelecting?: boolean;
}

export function GameSelector({ currentGameId, isHost, onSelectGame, isSelecting = false }: GameSelectorProps) {
    const [games, setGames] = useState<Game[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [dialogOpen, setDialogOpen] = useState(false);
    const [selectedGameId, setSelectedGameId] = useState<string | null>(null);

    useEffect(() => {
        loadGames();
    }, []);

    // New useEffect to retry loading games if currentGameId changes and the game isn't found
    useEffect(() => {
        if (currentGameId && games.length > 0) {
            const found = games.find(g => g.gameId === currentGameId);

            // If the game ID exists but isn't in our list, it might be stale.
            // If not loading, re-run the fetch to see if the game list has changed on the server.
            if (!found && !loading) {
                console.warn(`Game ID ${currentGameId} not found in local list. Retrying fetch.`);
                loadGames();
            }
        }
    }, [currentGameId, games, loading]); // Added loading as a dependency

    const loadGames = async () => {
        try {
            setLoading(true);
            const result = await fetchGames();
            setGames(result);
            setError(null);
        } catch (err) {
            console.error(err);
            setError("Failed to load games");
        } finally {
            setLoading(false);
        }
    };

    const handleOpenDialog = () => {
        setSelectedGameId(currentGameId);
        setDialogOpen(true);
    };

    const handleCloseDialog = () => {
        setDialogOpen(false);
        setSelectedGameId(null);
    };

    const handleConfirmSelection = () => {
        if (selectedGameId) {
            onSelectGame(selectedGameId);
            handleCloseDialog();
        }
    };

    const currentGame = games.find(g => g.gameId === currentGameId);

    // --- NON-HOST VIEW ---
    if (!isHost) {
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
                        <SportsEsportsRoundedIcon sx={{ fontSize: 20, color: "#9d7dff" }} />
                        <Typography variant="subtitle2" sx={{ color: "#fff", fontWeight: 600 }}>
                            Selected Game
                        </Typography>
                    </Stack>

                    {/* Display loading state if the game ID is present but the details are missing */}
                    {(loading || (currentGameId && !currentGame)) ? (
                        <Stack direction="row" spacing={1} alignItems="center">
                            <CircularProgress size={16} sx={{ color: "#9d7dff" }} />
                            <Typography variant="body2" sx={{ color: "#aaa" }}>
                                {currentGameId ? "Loading game details..." : "Loading games list..."}
                            </Typography>
                        </Stack>
                    ) : currentGame ? (
                        // Game found, display name
                        <Typography variant="body2" sx={{ color: "#d0d0e5" }}>
                            {currentGame.name}
                        </Typography>
                    ) : (
                        // No game ID set yet
                        <Typography variant="body2" sx={{ color: "#aaa", fontStyle: "italic" }}>
                            Waiting for host to select a game...
                        </Typography>
                    )}
                </Stack>
            </Box>
        );
    }

    // --- HOST VIEW --- (Remains the same)
    return (
        <>
            <Box
                sx={{
                    bgcolor: "rgba(0, 0, 0, 0.3)",
                    borderRadius: 2,
                    p: 2,
                    border: "1px solid rgba(255, 255, 255, 0.1)",
                }}
            >
                <Stack spacing={2}>
                    <Stack direction="row" alignItems="center" spacing={1}>
                        <SportsEsportsRoundedIcon sx={{ fontSize: 20, color: "#9d7dff" }} />
                        <Typography variant="subtitle2" sx={{ color: "#fff", fontWeight: 600 }}>
                            Game Selection
                        </Typography>
                    </Stack>

                    {currentGame ? (
                        <Stack spacing={1.5}>
                            <Typography variant="body2" sx={{ color: "#d0d0e5" }}>
                                Current: <strong>{currentGame.name}</strong>
                            </Typography>
                            <Button
                                variant="outlined"
                                size="small"
                                onClick={handleOpenDialog}
                                disabled={isSelecting}
                                sx={{
                                    borderColor: "#9d7dff",
                                    color: "#9d7dff",
                                    textTransform: "none",
                                    '&:hover': {
                                        borderColor: "#7f5aff",
                                        bgcolor: "rgba(157, 125, 255, 0.1)",
                                    }
                                }}
                            >
                                Change Game
                            </Button>
                        </Stack>
                    ) : (
                        <Button
                            variant="contained"
                            fullWidth
                            onClick={handleOpenDialog}
                            disabled={isSelecting}
                            sx={{
                                bgcolor: "#9d7dff",
                                '&:hover': { bgcolor: '#7f5aff' },
                                textTransform: "none",
                                fontWeight: 600,
                            }}
                        >
                            Select a Game
                        </Button>
                    )}
                </Stack>
            </Box>

            {/* Game Selection Dialog */}
            <Dialog
                open={dialogOpen}
                onClose={handleCloseDialog}
                maxWidth="md"
                fullWidth
                PaperProps={{
                    sx: {
                        bgcolor: "rgba(10, 16, 36, 0.98)",
                        backgroundImage: "none",
                        border: "1px solid rgba(255, 255, 255, 0.1)",
                    }
                }}
            >
                <DialogTitle sx={{ color: "#fff", borderBottom: "1px solid rgba(255, 255, 255, 0.1)" }}>
                    <Stack direction="row" alignItems="center" spacing={1}>
                        <SportsEsportsRoundedIcon sx={{ color: "#9d7dff" }} />
                        <Typography variant="h6" sx={{ fontWeight: 600 }}>
                            Choose a Game
                        </Typography>
                    </Stack>
                </DialogTitle>

                <DialogContent sx={{ mt: 2 }}>
                    {loading && (
                        <Stack alignItems="center" spacing={2} py={4}>
                            <CircularProgress sx={{ color: "#9d7dff" }} />
                            <Typography sx={{ color: "#d0d0e5" }}>Loading games...</Typography>
                        </Stack>
                    )}

                    {error && (
                        <Typography color="error" textAlign="center" py={4}>
                            {error}
                        </Typography>
                    )}

                    {!loading && !error && (
                        <Stack spacing={2}>
                            {games.map((game) => (
                                <Card
                                    key={game.gameId}
                                    sx={{
                                        bgcolor: selectedGameId === game.gameId
                                            ? "rgba(157, 125, 255, 0.15)"
                                            : "rgba(255, 255, 255, 0.05)",
                                        border: selectedGameId === game.gameId
                                            ? "2px solid #9d7dff"
                                            : "1px solid rgba(255, 255, 255, 0.1)",
                                        cursor: "pointer",
                                        transition: "all 0.2s ease",
                                        '&:hover': {
                                            bgcolor: "rgba(157, 125, 255, 0.1)",
                                            borderColor: "#9d7dff",
                                            transform: "translateX(4px)",
                                        }
                                    }}
                                    onClick={() => setSelectedGameId(game.gameId)}
                                >
                                    <CardContent>
                                        <Stack direction="row" justifyContent="space-between" alignItems="center">
                                            <Stack spacing={0.5}>
                                                <Typography variant="h6" sx={{ color: "#fff", fontWeight: 600 }}>
                                                    {game.name}
                                                </Typography>
                                                <Typography variant="body2" sx={{ color: "#aaa" }}>
                                                    {game.description || "No description available"}
                                                </Typography>
                                            </Stack>
                                            {selectedGameId === game.gameId && (
                                                <CheckCircleIcon sx={{ color: "#9d7dff", fontSize: 32 }} />
                                            )}
                                        </Stack>
                                    </CardContent>
                                </Card>
                            ))}
                        </Stack>
                    )}
                </DialogContent>

                <DialogActions sx={{ borderTop: "1px solid rgba(255, 255, 255, 0.1)", p: 2 }}>
                    <Button
                        onClick={handleCloseDialog}
                        sx={{ color: "#aaa", textTransform: "none" }}
                    >
                        Cancel
                    </Button>
                    <Button
                        onClick={handleConfirmSelection}
                        disabled={!selectedGameId || isSelecting}
                        variant="contained"
                        sx={{
                            bgcolor: "#9d7dff",
                            '&:hover': { bgcolor: '#7f5aff' },
                            textTransform: "none",
                            fontWeight: 600,
                        }}
                    >
                        {isSelecting ? "Selecting..." : "Confirm Selection"}
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
}