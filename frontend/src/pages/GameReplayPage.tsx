import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import {
    Box,
    Typography,
    CircularProgress,
    Alert,
    Paper,
    Button,
    Slider,
    Stack,
    Chip,
    Card,
    CardContent,
    IconButton,
    Tooltip,
} from "@mui/material";
import PlayArrowIcon from "@mui/icons-material/PlayArrow";
import PauseIcon from "@mui/icons-material/Pause";
import SkipPreviousIcon from "@mui/icons-material/SkipPrevious";
import SkipNextIcon from "@mui/icons-material/SkipNext";
import DownloadIcon from "@mui/icons-material/Download";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || "http://localhost:8083";

interface MoveReplay {
    moveNumber: number;
    playerType: string | null;
    playerSide: string | null;
    boardState: string;
    legalMoves: string;
    actualMove: number | null;
    aiHardRecommendedMove: number | null;
    aiHardConfidence: number | null;
    aiHardWinProbability: number | null;
    aiMlRecommendedMove: number | null;
    aiMlConfidence: number | null;
    aiMlWinProbability: number | null;
    timestamp: string | null;
}

interface GameReplay {
    sessionId: string;
    gameId: string;
    player1Type: string | null;
    player2Type: string | null;
    gameResult: string | null;
    startTime: string | null;
    endTime: string | null;
    moves: MoveReplay[];
}

function parseBoard(serializedBoard: string): number[][] {
    const cells = serializedBoard.split(" ");
    const board: number[][] = [];

    for (let row = 0; row < 6; row++) {
        board[row] = [];
        for (let col = 0; col < 7; col++) {
            const cell = cells[row * 7 + col];
            if (cell === "a") board[row][col] = 1;
            else if (cell === "b") board[row][col] = 2;
            else board[row][col] = 0;
        }
    }

    return board;
}

function Connect4Board({
    board,
    highlightCol,
    highlightRow,
    aiHardRecommendedCol,
    aiMlRecommendedCol,
    showAiHard,
    showAiMl,
    actualMove
}: {
    board: number[][];
    highlightCol: number | null;
    highlightRow: number | null;
    aiHardRecommendedCol: number | null;
    aiMlRecommendedCol: number | null;
    showAiHard: boolean;
    showAiMl: boolean;
    actualMove: number | null;
}) {
    const findEmptyRow = (col: number): number => {
        for (let row = 5; row >= 0; row--) {
            if (board[row][col] === 0) return row;
        }
        return -1;
    };

    const aiHardAgreesWithActual = showAiHard && aiHardRecommendedCol === actualMove;
    const aiMlAgreesWithActual = showAiMl && aiMlRecommendedCol === actualMove;

    const aiHardRow = showAiHard && aiHardRecommendedCol !== null && !aiHardAgreesWithActual
        ? findEmptyRow(aiHardRecommendedCol) : -1;
    const aiMlRow = showAiMl && aiMlRecommendedCol !== null && !aiMlAgreesWithActual
        ? findEmptyRow(aiMlRecommendedCol) : -1;

    return (
        <Box sx={{ display: "flex", flexDirection: "column", gap: 0.5 }}>
            {board.map((row, rowIdx) => (
                <Box key={rowIdx} sx={{ display: "flex", gap: 0.5 }}>
                    {row.map((cell, colIdx) => {
                        const isHighlighted = highlightCol === colIdx && highlightRow === rowIdx;
                        const isAiHardSuggestion = showAiHard && aiHardRecommendedCol === colIdx && aiHardRow === rowIdx && cell === 0;
                        const isAiMlSuggestion = showAiMl && aiMlRecommendedCol === colIdx && aiMlRow === rowIdx && cell === 0;

                        const isActualMoveCell = isHighlighted;
                        const aiHardAgreesHere = isActualMoveCell && aiHardAgreesWithActual;
                        const aiMlAgreesHere = isActualMoveCell && aiMlAgreesWithActual;
                        const anyAiAgrees = aiHardAgreesHere || aiMlAgreesHere;

                        let borderStyle = "1px solid #bdbdbd";
                        let boxShadowStyle = cell !== 0 ? "inset 0 -3px 6px rgba(0,0,0,0.2)" : "none";

                        if (isHighlighted && anyAiAgrees) {
                            borderStyle = "4px solid #4caf50";
                            boxShadowStyle = "0 0 12px rgba(76, 175, 80, 0.8), 0 0 20px rgba(33, 150, 243, 0.5)";
                        } else if (isHighlighted) {
                            borderStyle = "4px solid #2196f3";
                            boxShadowStyle = "0 0 12px rgba(33, 150, 243, 0.8)";
                        } else if (isAiHardSuggestion && isAiMlSuggestion) {
                            borderStyle = "4px solid";
                            boxShadowStyle = "0 0 8px rgba(156, 39, 176, 0.6), 0 0 8px rgba(255, 152, 0, 0.6)";
                        } else if (isAiHardSuggestion) {
                            borderStyle = "4px dashed #ff9800";
                            boxShadowStyle = "0 0 8px rgba(255, 152, 0, 0.6)";
                        } else if (isAiMlSuggestion) {
                            borderStyle = "4px dashed #9c27b0";
                            boxShadowStyle = "0 0 8px rgba(156, 39, 176, 0.6)";
                        }

                        return (
                            <Box
                                key={colIdx}
                                sx={{
                                    width: 48,
                                    height: 48,
                                    borderRadius: "50%",
                                    backgroundColor:
                                        cell === 1 ? "#f44336" :
                                        cell === 2 ? "#ffeb3b" :
                                        isAiHardSuggestion && isAiMlSuggestion ? "linear-gradient(135deg, rgba(255,152,0,0.3), rgba(156,39,176,0.3))" :
                                        isAiHardSuggestion ? "rgba(255, 152, 0, 0.2)" :
                                        isAiMlSuggestion ? "rgba(156, 39, 176, 0.2)" :
                                        "#e0e0e0",
                                    border: borderStyle,
                                    borderImage: isAiHardSuggestion && isAiMlSuggestion && !isHighlighted ? "linear-gradient(135deg, #ff9800, #9c27b0) 1" : "none",
                                    boxShadow: boxShadowStyle,
                                    transition: "all 0.2s ease-in-out",
                                    position: "relative",
                                }}
                            >
                                {}
                                {isHighlighted && anyAiAgrees && (
                                    <Box sx={{
                                        position: "absolute",
                                        top: "-8px",
                                        right: "-8px",
                                        fontSize: "14px",
                                        backgroundColor: "#4caf50",
                                        borderRadius: "50%",
                                        width: 24,
                                        height: 24,
                                        display: "flex",
                                        alignItems: "center",
                                        justifyContent: "center",
                                        boxShadow: "0 2px 4px rgba(0,0,0,0.3)"
                                    }}>
                                        {aiHardAgreesHere && aiMlAgreesHere ? "✓✓" : "✓"}
                                    </Box>
                                )}
                                {}
                                {(isAiHardSuggestion || isAiMlSuggestion) && cell === 0 && (
                                    <Box sx={{
                                        position: "absolute",
                                        top: "50%",
                                        left: "50%",
                                        transform: "translate(-50%, -50%)",
                                        fontSize: "16px",
                                        opacity: 0.8
                                    }}>
                                        {isAiHardSuggestion && isAiMlSuggestion ? "" : isAiHardSuggestion ? "" : ""}
                                    </Box>
                                )}
                            </Box>
                        );
                    })}
                </Box>
            ))}
            {}
            <Box sx={{ display: "flex", gap: 0.5, mt: 1 }}>
                {[0, 1, 2, 3, 4, 5, 6].map((col) => (
                    <Box
                        key={col}
                        sx={{
                            width: 48,
                            textAlign: "center",
                            color: highlightCol === col ? "#2196f3" :
                                   (showAiHard && aiHardRecommendedCol === col) ? "#ff9800" :
                                   (showAiMl && aiMlRecommendedCol === col) ? "#9c27b0" :
                                   "#757575",
                            fontWeight: highlightCol === col ||
                                       (showAiHard && aiHardRecommendedCol === col) ||
                                       (showAiMl && aiMlRecommendedCol === col) ? "bold" : "normal",
                        }}
                    >
                        {col}
                    </Box>
                ))}
            </Box>
            {}
            <Box sx={{ display: "flex", gap: 2, mt: 2, justifyContent: "center", flexWrap: "wrap" }}>
                <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
                    <Box sx={{ width: 16, height: 16, borderRadius: "50%", border: "3px solid #2196f3" }} />
                    <Typography variant="caption">Actual Move</Typography>
                </Box>
                <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
                    <Box sx={{ width: 16, height: 16, borderRadius: "50%", border: "3px solid #4caf50", position: "relative" }}>
                        <Box sx={{ position: "absolute", top: -4, right: -4, fontSize: 8, backgroundColor: "#4caf50", borderRadius: "50%", width: 10, height: 10 }} />
                    </Box>
                    <Typography variant="caption">AI Agrees</Typography>
                </Box>
                {showAiHard && (
                    <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
                        <Box sx={{ width: 16, height: 16, borderRadius: "50%", border: "3px dashed #ff9800" }} />
                        <Typography variant="caption"> MCTS</Typography>
                    </Box>
                )}
                {showAiMl && (
                    <Box sx={{ display: "flex", alignItems: "center", gap: 0.5 }}>
                        <Box sx={{ width: 16, height: 16, borderRadius: "50%", border: "3px dashed #9c27b0" }} />
                        <Typography variant="caption"> ML</Typography>
                    </Box>
                )}
            </Box>
        </Box>
    );
}

function AiAnalysisPanel({ move, player1Type, player2Type }: {
    move: MoveReplay | null;
    player1Type: string | null;
    player2Type: string | null;
}) {
    if (!move) return null;

    const isAiMlPlaying = player1Type === "AI_ML" || player2Type === "AI_ML";
    const isAiHardPlaying = player1Type?.startsWith("AI_") && player1Type !== "AI_ML" ||
                            player2Type?.startsWith("AI_") && player2Type !== "AI_ML";


    const showAiHard = !isAiHardPlaying && move.aiHardRecommendedMove !== null;
    const showAiMl = !isAiMlPlaying && move.aiMlRecommendedMove !== null;
    const hasAnyAiData = showAiHard || showAiMl;

    const aiHardDisagreement = move.actualMove !== null &&
                               move.aiHardRecommendedMove !== null &&
                               move.actualMove !== move.aiHardRecommendedMove;
    const aiMlDisagreement = move.actualMove !== null &&
                             move.aiMlRecommendedMove !== null &&
                             move.actualMove !== move.aiMlRecommendedMove;

    return (
        <Card sx={{ height: "100%" }}>
            <CardContent>
                <Typography variant="h6" gutterBottom>
                    AI Comparison
                </Typography>

                {!hasAnyAiData ? (
                    <Typography color="text.secondary">
                        No shadow AI analysis data for this move
                    </Typography>
                ) : (
                    <Stack spacing={3}>
                        {}
                        <Box>
                            <Typography variant="subtitle2" color="text.secondary">
                                Actual Move Played
                            </Typography>
                            <Typography variant="h5">
                                Column {move.actualMove ?? "N/A"}
                            </Typography>
                            <Chip
                                label={move.playerType || "Unknown"}
                                size="small"
                                sx={{ mt: 0.5 }}
                            />
                        </Box>

                        {}
                        {showAiHard && (
                            <Paper elevation={2} sx={{ p: 2, bgcolor: "#fff3e0" }}>
                                <Typography variant="subtitle1" fontWeight="bold" color="primary">
                                      MCTS Would Play
                                </Typography>
                                <Typography
                                    variant="h5"
                                    color={aiHardDisagreement ? "warning.main" : "success.main"}
                                >
                                    Column {move.aiHardRecommendedMove}
                                    {aiHardDisagreement && (
                                        <Chip
                                            label="Different!"
                                            color="warning"
                                            size="small"
                                            sx={{ ml: 1 }}
                                        />
                                    )}
                                </Typography>
                                {move.aiHardConfidence !== null && (
                                    <Typography variant="body2" color="text.secondary">
                                        Confidence: {(move.aiHardConfidence * 100).toFixed(1)}%
                                    </Typography>
                                )}
                                {move.aiHardWinProbability !== null && (
                                    <Tooltip title="Board evaluation score from MCTS. Positive = favorable, Negative = unfavorable. Large values indicate near-certain outcomes.">
                                        <Typography variant="body2" color="text.secondary" sx={{ cursor: "help" }}>
                                            Eval Score: {move.aiHardWinProbability.toFixed(0)}
                                        </Typography>
                                    </Tooltip>
                                )}
                            </Paper>
                        )}

                        {}
                        {showAiMl && (
                            <Paper elevation={2} sx={{ p: 2, bgcolor: "#e3f2fd" }}>
                                <Typography variant="subtitle1" fontWeight="bold" color="secondary">
                                     AI_ML  Would Play
                                </Typography>
                                <Typography
                                    variant="h5"
                                    color={aiMlDisagreement ? "warning.main" : "success.main"}
                                >
                                    Column {move.aiMlRecommendedMove}
                                    {aiMlDisagreement && (
                                        <Chip
                                            label="Different!"
                                            color="warning"
                                            size="small"
                                            sx={{ ml: 1 }}
                                        />
                                    )}
                                </Typography>
                                {move.aiMlConfidence !== null && (
                                    <Typography variant="body2" color="text.secondary">
                                        Confidence: {(move.aiMlConfidence * 100).toFixed(1)}%
                                    </Typography>
                                )}
                            </Paper>
                        )}

                        {}
                        {showAiHard && showAiMl && (
                            <Box sx={{ textAlign: "center", mt: 1 }}>
                                {move.aiHardRecommendedMove === move.aiMlRecommendedMove ? (
                                    <Chip
                                        label=" Both AIs agree!"
                                        color="success"
                                    />
                                ) : (
                                    <Chip
                                        label=" AIs disagree"
                                        color="warning"
                                    />
                                )}
                            </Box>
                        )}
                    </Stack>
                )}
            </CardContent>
        </Card>
    );
}

export function GameReplayPage() {
    const { sessionId } = useParams<{ sessionId: string }>();
    const navigate = useNavigate();

    const [replay, setReplay] = useState<GameReplay | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [currentMoveIndex, setCurrentMoveIndex] = useState(0);
    const [isPlaying, setIsPlaying] = useState(false);

    useEffect(() => {
        async function fetchReplay() {
            try {
                const response = await axios.get(`${BACKEND_URL}/api/gameplay/export/${sessionId}/replay`);
                setReplay(response.data);
            } catch (err) {
                if (axios.isAxiosError(err)) {
                    setError(err.response?.data?.message || err.message || "Failed to fetch game replay");
                } else {
                    setError("Unknown error");
                }
            } finally {
                setLoading(false);
            }
        }

        if (sessionId) {
            fetchReplay();
        }
    }, [sessionId]);

    useEffect(() => {
        if (!isPlaying || !replay) return;

        const interval = setInterval(() => {
            setCurrentMoveIndex((prev) => {
                if (prev >= replay.moves.length - 1) {
                    setIsPlaying(false);
                    return prev;
                }
                return prev + 1;
            });
        }, 1000);

        return () => clearInterval(interval);
    }, [isPlaying, replay]);

    const handleDownloadCSV = async () => {
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

    if (loading) {
        return (
            <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                <CircularProgress />
            </Box>
        );
    }

    if (error || !replay) {
        return (
            <Box sx={{ p: 3 }}>
                <Alert severity="error">{error || "Game not found"}</Alert>
                <Button onClick={() => navigate(-1)} sx={{ mt: 2 }}>
                    Go Back
                </Button>
            </Box>
        );
    }

    const currentMove = replay.moves[currentMoveIndex] || null;
    const board = currentMove ? parseBoard(currentMove.boardState) : Array(6).fill(Array(7).fill(0));

    const previousMove = currentMoveIndex > 0 ? replay.moves[currentMoveIndex - 1] : null;
    const previousBoard = previousMove ? parseBoard(previousMove.boardState) : Array(6).fill(Array(7).fill(0));

    let highlightRow: number | null = null;
    let highlightCol: number | null = currentMove?.actualMove ?? null;

    if (currentMove && previousMove && highlightCol !== null) {
        for (let row = 5; row >= 0; row--) {
            if (board[row][highlightCol] !== 0 && previousBoard[row]?.[highlightCol] === 0) {
                highlightRow = row;
                break;
            }
        }
    } else if (currentMoveIndex === 0 && currentMove && highlightCol !== null) {
        for (let row = 5; row >= 0; row--) {
            if (board[row][highlightCol] !== 0) {
                highlightRow = row;
                break;
            }
        }
    }

    const isAiMlPlaying = replay.player1Type === "AI_ML" || replay.player2Type === "AI_ML";
    const isAiHardPlaying = (replay.player1Type?.startsWith("AI_") && replay.player1Type !== "AI_ML") ||
                            (replay.player2Type?.startsWith("AI_") && replay.player2Type !== "AI_ML");

    const showAiHard = !isAiHardPlaying && currentMove?.aiHardRecommendedMove !== null;
    const showAiMl = !isAiMlPlaying && currentMove?.aiMlRecommendedMove !== null;

    return (
        <Box sx={{ p: 3 }}>
            {}
            <Stack direction="row" justifyContent="space-between" alignItems="center" mb={3}>
                <Stack direction="row" alignItems="center" spacing={2}>
                    <IconButton onClick={() => navigate(-1)}>
                        <ArrowBackIcon />
                    </IconButton>
                    <Typography variant="h4">Game Replay</Typography>
                </Stack>

                <Tooltip title="Download CSV">
                    <IconButton onClick={handleDownloadCSV} color="primary">
                        <DownloadIcon />
                    </IconButton>
                </Tooltip>
            </Stack>

            {}
            <Paper sx={{ p: 2, mb: 3 }}>
                <Stack direction="row" spacing={2} flexWrap="wrap">
                    <Chip label={`Player 1: ${replay.player1Type || "Unknown"}`} color="error" />
                    <Chip label={`Player 2: ${replay.player2Type || "Unknown"}`} sx={{ bgcolor: "#ffeb3b" }} />
                    <Chip
                        label={`Result: ${replay.gameResult || "Unknown"}`}
                        color={replay.gameResult === "DRAW" ? "default" : "success"}
                    />
                    <Chip label={`Total Moves: ${replay.moves.length}`} variant="outlined" />
                </Stack>
            </Paper>

            <Box sx={{ display: "flex", flexDirection: { xs: "column", md: "row" }, gap: 3 }}>
                {/* Board */}
                <Box sx={{ flex: 1 }}>
                    <Paper sx={{ p: 3 }}>
                        <Typography variant="h6" gutterBottom>
                            Move {currentMoveIndex + 1} of {replay.moves.length}
                            {currentMove && (
                                <Chip
                                    label={`${currentMove.playerSide} (${currentMove.playerType})`}
                                    size="small"
                                    sx={{ ml: 2 }}
                                />
                            )}
                        </Typography>

                        <Box sx={{ display: "flex", justifyContent: "center", my: 2 }}>
                            <Connect4Board
                                board={board}
                                highlightCol={currentMove?.actualMove ?? null}
                                highlightRow={highlightRow}
                                aiHardRecommendedCol={currentMove?.aiHardRecommendedMove ?? null}
                                aiMlRecommendedCol={currentMove?.aiMlRecommendedMove ?? null}
                                showAiHard={showAiHard}
                                showAiMl={showAiMl}
                                actualMove={currentMove?.actualMove ?? null}
                            />
                        </Box>

                        {}
                        <Stack direction="row" justifyContent="center" alignItems="center" spacing={2}>
                            <IconButton
                                onClick={() => setCurrentMoveIndex(0)}
                                disabled={currentMoveIndex === 0}
                            >
                                <SkipPreviousIcon />
                            </IconButton>

                            <IconButton
                                onClick={() => setCurrentMoveIndex(Math.max(0, currentMoveIndex - 1))}
                                disabled={currentMoveIndex === 0}
                            >
                                <SkipPreviousIcon fontSize="small" />
                            </IconButton>

                            <IconButton
                                onClick={() => setIsPlaying(!isPlaying)}
                                color="primary"
                                size="large"
                            >
                                {isPlaying ? <PauseIcon /> : <PlayArrowIcon />}
                            </IconButton>

                            <IconButton
                                onClick={() => setCurrentMoveIndex(Math.min(replay.moves.length - 1, currentMoveIndex + 1))}
                                disabled={currentMoveIndex >= replay.moves.length - 1}
                            >
                                <SkipNextIcon fontSize="small" />
                            </IconButton>

                            <IconButton
                                onClick={() => setCurrentMoveIndex(replay.moves.length - 1)}
                                disabled={currentMoveIndex >= replay.moves.length - 1}
                            >
                                <SkipNextIcon />
                            </IconButton>
                        </Stack>

                        {/* Move slider */}
                        <Box sx={{ px: 2, mt: 2 }}>
                            <Slider
                                value={currentMoveIndex}
                                min={0}
                                max={replay.moves.length - 1}
                                onChange={(_: Event, value: number | number[]) => setCurrentMoveIndex(value as number)}
                                valueLabelDisplay="auto"
                                valueLabelFormat={(v: number) => `Move ${v + 1}`}
                            />
                        </Box>
                    </Paper>
                </Box>

                <Box sx={{ flex: 1 }}>
                    <AiAnalysisPanel
                        move={currentMove}
                        player1Type={replay.player1Type}
                        player2Type={replay.player2Type}
                    />
                </Box>
            </Box>
        </Box>
    );
}

