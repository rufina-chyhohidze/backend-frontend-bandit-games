import { Stack, Typography, Button, CircularProgress, Snackbar, Alert } from "@mui/material";
import GroupIcon from "@mui/icons-material/GroupRounded";
import { useState, useEffect } from "react";
import { useCreateLobby } from "../../hooks/useLobby";

interface NoLobbyStateProps {
    playerId?: string;
    onLobbyCreated?: () => void;
}

export function NoLobbyState({ onLobbyCreated }: NoLobbyStateProps) {
    const { mutateAsync: createLobbyMutate, isPending, error } = useCreateLobby();
    const [snackbarOpen, setSnackbarOpen] = useState(false);

    useEffect(() => {
        if (error) {
            setSnackbarOpen(true);
        }
    }, [error]);

    const handleCreateLobby = async () => {
        try {
            await createLobbyMutate();

            setSnackbarOpen(false);
            onLobbyCreated?.();

        } catch (e) {
            // Error is handled by the hook
        }
    };

    const getErrorMessage = (err: unknown): string => {
        if (!err) return "An unknown error occurred.";

        if (String(err).includes("PlayerAlreadyInLobbyException")) {
            return "Failed to create lobby. You are already in an active lobby.";
        }

        return "Failed to create lobby. Please try again.";
    };

    return (
        <Stack spacing={3} alignItems="center" sx={{ py: 6, width: '100%' }}>
            <GroupIcon sx={{ fontSize: 60, color: "#9d7dff" }} />
            <Typography variant="h5" sx={{ color: "#ffffff", fontWeight: 600 }}>
                No Active Lobby Found
            </Typography>
            <Typography variant="body1" sx={{ color: "#d0d0e5", maxWidth: 400, textAlign: 'center' }}>
                You are not hosting or in a lobby. Create one now to start a game!
            </Typography>

            <Button
                variant="contained"
                onClick={handleCreateLobby}
                disabled={isPending}
                startIcon={isPending ? <CircularProgress size={20} color="inherit" /> : <GroupIcon />}
                sx={{
                    textTransform: "none",
                    fontWeight: 600,
                    px: 4,
                    py: 1.5,
                    borderRadius: 100,
                    background: "linear-gradient(120deg, #9d7dff 0%, #00dccc 100%)",
                    "&:hover": {
                        background: "linear-gradient(120deg, #b091ff 0%, #11eddc 100%)",
                    },
                }}
            >
                {isPending ? "Creating..." : "Create Lobby"}
            </Button>

            <Snackbar
                open={snackbarOpen}
                autoHideDuration={6000}
                onClose={() => setSnackbarOpen(false)}
            >
                <Alert
                    onClose={() => setSnackbarOpen(false)}
                    severity="error"
                    sx={{ width: '100%' }}
                >
                    {getErrorMessage(error)}
                </Alert>
            </Snackbar>
        </Stack>
    );
}