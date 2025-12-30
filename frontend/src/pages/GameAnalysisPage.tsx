import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {
    Box,
    Typography,
    CircularProgress,
    Alert,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Button,
    Chip,
    Stack,
    TextField,
    InputAdornment,
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import DownloadIcon from "@mui/icons-material/Download";
import AnalyticsIcon from "@mui/icons-material/Analytics";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || "http://localhost:8083";

interface GameSession {
    sessionId: string;
    gameId: string;
    player1Type: string;
    player2Type: string;
    gameResult: string | null;
    startTime: string | null;
    endTime: string | null;
    moveCount: number;
}

export function GameAnalysisPage() {
    const navigate = useNavigate();
    const [games, setGames] = useState<GameSession[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [searchTerm, setSearchTerm] = useState("");

    const fetchGames = async () => {
        try {
            const response = await axios.get(`${BACKEND_URL}/api/gameplay/export/sessions`);
            setGames(response.data);
            setError(null);
        } catch (err) {
            if (axios.isAxiosError(err)) {
                setError(err.response?.data?.message || err.message || "Failed to fetch game sessions");
            } else {
                setError("Unknown error");
            }
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchGames();
    }, []);

    useEffect(() => {
        const interval = setInterval(() => {
            fetchGames();
        }, 10000);

        return () => clearInterval(interval);
    }, []);

    const handleReplay = (sessionId: string) => {
        navigate(`/games/replay/${sessionId}`);
    };

    const handleDownload = async (sessionId: string) => {
        try {
            const response = await axios.get(
                `${BACKEND_URL}/api/gameplay/export/${sessionId}`,
                { responseType: 'blob' }
            );

            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `game_${sessionId}.csv`);
            document.body.appendChild(link);
            link.click();
            link.remove();
            window.URL.revokeObjectURL(url);
        } catch (err) {
            console.error("Failed to download CSV:", err);
            alert("Failed to download CSV. Please try again.");
        }
    };

    const filteredGames = games.filter((game) => {
        const search = searchTerm.toLowerCase();
        return (
            game.sessionId.toLowerCase().includes(search) ||
            game.player1Type.toLowerCase().includes(search) ||
            game.player2Type.toLowerCase().includes(search) ||
            (game.gameResult?.toLowerCase().includes(search) ?? false)
        );
    });

    const getResultColor = (result: string | null) => {
        if (!result) return "default";
        if (result === "DRAW") return "warning";
        return "success";
    };

    const getPlayerTypeColor = (type: string) => {
        if (type === "HUMAN") return "primary";
        if (type === "AI_ML") return "secondary";
        if (type.startsWith("AI_")) return "info";
        return "default";
    };

    if (loading && games.length === 0) {
        return (
            <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                <CircularProgress />
            </Box>
        );
    }

    return (
        <Box sx={{ p: 3,  pt: 10  }}>
            <Stack direction="row" alignItems="center" justifyContent="space-between" mb={3}>
                <Stack direction="row" alignItems="center" spacing={2}>
                    <AnalyticsIcon sx={{ fontSize: 40, color: "primary.main" }} />
                    <Box>
                        <Typography variant="h4">Game Analysis</Typography>
                        <Typography variant="body2" color="text.secondary">
                            View and analyze completed games with AI shadow recommendations
                        </Typography>
                    </Box>
                </Stack>
                <Button
                    variant="outlined"
                    onClick={fetchGames}
                    disabled={loading}
                    startIcon={loading ? <CircularProgress size={16} /> : null}
                >
                    {loading ? "Refreshing..." : "Refresh"}
                </Button>
            </Stack>

            {error && (
                <Alert severity="error" sx={{ mb: 2 }}>
                    {error}
                </Alert>
            )}

            <Paper sx={{ p: 2, mb: 3 }}>
                <TextField
                    fullWidth
                    placeholder="Search by session ID, player type, or result..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <SearchIcon />
                            </InputAdornment>
                        ),
                    }}
                    size="small"
                />
            </Paper>

            <Stack direction="row" spacing={2} mb={3}>
                <Paper sx={{ p: 2, flex: 1 }}>
                    <Typography variant="h6">{games.length}</Typography>
                    <Typography variant="body2" color="text.secondary">
                        Total Games
                    </Typography>
                </Paper>
                <Paper sx={{ p: 2, flex: 1 }}>
                    <Typography variant="h6">
                        {games.filter((g) => g.player1Type === "HUMAN" || g.player2Type === "HUMAN").length}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        Human Games
                    </Typography>
                </Paper>
                <Paper sx={{ p: 2, flex: 1 }}>
                    <Typography variant="h6">
                        {games.filter((g) => g.player1Type.startsWith("AI") && g.player2Type.startsWith("AI")).length}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        AI vs AI Games
                    </Typography>
                </Paper>
            </Stack>

            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow sx={{ bgcolor: "grey.100" }}>
                            <TableCell>Session ID</TableCell>
                            <TableCell>Player 1</TableCell>
                            <TableCell>Player 2</TableCell>
                            <TableCell>Result</TableCell>
                            <TableCell>Moves</TableCell>
                            <TableCell>Date</TableCell>
                            <TableCell align="right">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {filteredGames.length === 0 ? (
                            <TableRow>
                                <TableCell colSpan={7} align="center">
                                    <Typography color="text.secondary" py={4}>
                                        No games found
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        ) : (
                            filteredGames.map((game) => (
                                <TableRow key={game.sessionId} hover>
                                    <TableCell>
                                        <Typography variant="body2" sx={{ fontFamily: "monospace" }}>
                                            {game.sessionId.substring(0, 8)}...
                                        </Typography>
                                    </TableCell>
                                    <TableCell>
                                        <Chip
                                            label={game.player1Type}
                                            size="small"
                                            color={getPlayerTypeColor(game.player1Type) as any}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Chip
                                            label={game.player2Type}
                                            size="small"
                                            color={getPlayerTypeColor(game.player2Type) as any}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <Chip
                                            label={game.gameResult || "In Progress"}
                                            size="small"
                                            color={getResultColor(game.gameResult) as any}
                                            variant={game.gameResult ? "filled" : "outlined"}
                                        />
                                    </TableCell>
                                    <TableCell>{game.moveCount}</TableCell>
                                    <TableCell>
                                        {game.startTime
                                            ? new Date(game.startTime).toLocaleDateString()
                                            : "-"}
                                    </TableCell>
                                    <TableCell align="right">
                                        <Stack direction="row" spacing={1} justifyContent="flex-end">
                                            <Button
                                                size="small"
                                                variant="contained"
                                                startIcon={<PlayArrowIcon />}
                                                onClick={() => handleReplay(game.sessionId)}
                                            >
                                                Replay
                                            </Button>
                                            <Button
                                                size="small"
                                                variant="outlined"
                                                startIcon={<DownloadIcon />}
                                                onClick={() => handleDownload(game.sessionId)}
                                            >
                                                CSV
                                            </Button>
                                        </Stack>
                                    </TableCell>
                                </TableRow>
                            ))
                        )}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
}

