import type { Game } from "../../models/Game";
import {
    Card,
    CardActionArea,
    CardContent,
    CardMedia,
    CardActions,
    Button,
    Typography,
} from "@mui/material";

interface GameCardProps {
    game: Game;
    onPlay: (game: Game) => void;
}

export function GameCard({ game, onPlay }: GameCardProps) {
    const handlePlayClick = () => onPlay(game);

    return (
        <Card
            sx={{
                height: "100%",
                display: "flex",
                flexDirection: "column",
            }}
            elevation={3}
        >
            <CardActionArea onClick={handlePlayClick} sx={{ flexGrow: 1 }}>
                {game.pictureUrl && (
                    <CardMedia
                        component="img"
                        height="160"
                        image={game.pictureUrl}
                        alt={game.name}
                    />
                )}
                <CardContent>
                    <Typography gutterBottom variant="h6" component="div">
                        {game.name}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {game.description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions sx={{ justifyContent: "flex-end", px: 2, pb: 2 }}>
                <Button size="small" variant="contained" onClick={handlePlayClick}>
                    Play
                </Button>
            </CardActions>
        </Card>
    );
}
