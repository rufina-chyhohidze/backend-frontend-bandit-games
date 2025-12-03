import {
    Card,
    CardActionArea,
    CardContent,
    CardMedia,
    CardActions,
    Button,
    Typography,
    IconButton,
    Tooltip,
} from "@mui/material";
import StarBorderIcon from "@mui/icons-material/StarBorder";
import StarIcon from "@mui/icons-material/Star";
import type {Game} from "../../models/game.ts";

interface GameCardProps {
    game: Game;
    onPlay: () => void;
    onViewAchievements: () => void;
    onFavorite: () => void;
    isFavorite: boolean;
}

export function GameCard({ game, onPlay, onViewAchievements, onFavorite, isFavorite }: GameCardProps) {
    return (
        <Card
            sx={{
                height: "100%",
                display: "flex",
                flexDirection: "column",
            }}
            elevation={3}
        >
            <CardActionArea onClick={onPlay} sx={{ flexGrow: 1 }}>
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

            <CardActions sx={{ justifyContent: "space-between", px: 2, pb: 2 }}>
                <Button size="small" variant="contained" onClick={onPlay}>
                    Play
                </Button>
                <Button size="small" variant="outlined" onClick={onViewAchievements}>
                    Achievements
                </Button>

                <Tooltip title={isFavorite ? "Favorite" : "Add to favorites"}>
                    <IconButton onClick={onFavorite} size="small">
                        {isFavorite ? <StarIcon color="warning" /> : <StarBorderIcon />}
                    </IconButton>
                </Tooltip>
            </CardActions>
        </Card>
    );
}
