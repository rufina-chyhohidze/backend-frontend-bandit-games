import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import type { Game } from "../models/Game";
import { fetchPlayableGames } from "../api/gamesApi";
import { GameList } from "../components/games/GameList";
import {
    Box,
    CircularProgress,
    Container,
    Typography,
    Alert,
    Button,
    Stack,
} from "@mui/material";

export function GamesPage() {
    const [games, setGames] = useState<Game[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const loadGames = async () => {
        try {
            setLoading(true);
            const result = await fetchPlayableGames();
            setGames(result);
            setError(null);
        } catch (err) {
            console.error(err);
            setError("Failed to load games. Please try again later.");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadGames();
    }, []);

    const handlePlay = (game: Game) => {
        const url = game.urlGameSession;

        if (url.startsWith("http")) {
            // External game (Chess)
            window.location.href = url;
        } else {
            // Internal route (Connect Four)
            navigate(url);
        }
    };

    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Stack spacing={3}>
                <Box>
                    <Typography variant="h4" component="h1" gutterBottom>
                        Available Games
                    </Typography>
                    <Typography
                        variant="body1"
                        sx={{ color: "#ffffff" }}
                    >
                        Choose a game to start playing. External games will open their own
                        game page, internal games stay inside BanditGames.
                    </Typography>
                </Box>

                {loading && (
                    <Box sx={{ display: "flex", justifyContent: "center", py: 4 }}>
                        <CircularProgress />
                    </Box>
                )}

                {!loading && error && (
                    <Stack spacing={2}>
                        <Alert severity="error">{error}</Alert>
                        <Button variant="outlined" onClick={loadGames}>
                            Retry
                        </Button>
                    </Stack>
                )}

                {!loading && !error && <GameList games={games} onPlay={handlePlay} />}
            </Stack>
        </Container>
    );
}
