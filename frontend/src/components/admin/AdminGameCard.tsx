import {
    Box,
    Typography,
    Button,
    Stack
} from "@mui/material";
import type { AdminGame } from "../../models/adminGame";

interface Props {
    game: AdminGame;
    onApprove: () => void;
    onReject: () => void;
}

export function AdminGameCard({ game, onApprove, onReject }: Props) {
    return (
        <Box
            sx={{
                borderRadius: 2,
                bgcolor: "rgba(255,255,255,0.07)",
                border: "1px solid rgba(255,255,255,0.15)",
                p: 2.5,
                mb: 2,
                color: "#cfd7ff",
            }}
        >
            <Typography variant="h6" sx={{ color: "#9dc6ff" }}>
                {game.name}
            </Typography>

            <Typography sx={{ fontSize: 14, color: "#aabaff", mt: 1 }}>
                {game.description}
            </Typography>

            <Stack direction="row" spacing={2} sx={{ mt: 2 }}>
                <Button
                    variant="contained"
                    onClick={onApprove}
                    sx={{
                        background: "linear-gradient(120deg, #5efc82, #00dc82)",
                        textTransform: "none",
                        fontWeight: 600,
                    }}
                >
                    Approve
                </Button>

                <Button
                    variant="outlined"
                    onClick={onReject}
                    sx={{
                        color: "#ff7d7d",
                        borderColor: "#ff7d7d",
                        textTransform: "none",
                        fontWeight: 600,
                    }}
                >
                    Reject
                </Button>
            </Stack>
        </Box>
    );
}
