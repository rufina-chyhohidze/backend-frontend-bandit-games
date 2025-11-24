import { Grid } from "@mui/material";
import type { Game } from "../../models/Game";
import { GameCard } from "./GameCard";

type Props = {
    games: Game[];
    onPlay: (game: Game) => void;
    onViewAchievements: (game: Game) => void;
};

export function GameList({ games, onPlay, onViewAchievements }: Props) {
    return (
        <Grid container spacing={3}>
            {games.map((game) => (
                <Grid item xs={12} sm={6} md={4} key={game.gameId}>
                    <GameCard
                        game={game}
                        onPlay={() => onPlay(game)}
                        onViewAchievements={() => onViewAchievements(game)}
                    />
                </Grid>
            ))}
        </Grid>
    );
}
