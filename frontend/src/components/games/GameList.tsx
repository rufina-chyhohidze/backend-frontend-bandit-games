import type { Game } from "../../models/Game";
import { GameCard } from "./GameCard";
import { Grid, Typography } from "@mui/material";

interface GameListProps {
    games: Game[];
    onPlay: (game: Game) => void;
}

export function GameList({ games, onPlay }: GameListProps) {
    if (games.length === 0) {
        return (
            <Typography variant="body1" color="text.secondary">
                No games available.
            </Typography>
        );
    }

    return (
        <Grid container spacing={3}>
            {games.map((g) => (
                <Grid item key={g.gameId} xs={12} sm={6} md={4} lg={3}>
                    <GameCard game={g} onPlay={onPlay} />
                </Grid>
            ))}
        </Grid>
    );
}
