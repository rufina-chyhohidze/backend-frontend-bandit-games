import { Box, Button, Typography } from "@mui/material";
import PersonRoundedIcon from "@mui/icons-material/PersonRounded";

export function AccessDeniedCard({ onLogin }: { onLogin: () => void }) {
    return (
        <Box
            sx={{
                minHeight: "100vh",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                background: "linear-gradient(135deg, #101a2a 0%, #2a1422 45%, #0e0f1a 100%)",
                p: 3,
            }}
        >
            <Box
                sx={{
                    width: { xs: "94vw", sm: 520 },
                    p: 4,
                    borderRadius: 5,
                    bgcolor: "rgba(10, 14, 28, 0.86)",
                    border: "1px solid rgba(255,255,255,0.1)",
                    textAlign: "center",
                    boxShadow: "0 26px 80px rgba(0,0,0,0.7)",
                    backdropFilter: "blur(10px)",
                }}
            >
                <PersonRoundedIcon sx={{ fontSize: 52, color: "#9d7dff", mb: 2 }} />
                <Typography variant="h5" sx={{ fontWeight: 800, color: "#fff" }}>
                    Access Denied
                </Typography>
                <Typography sx={{ color: "rgba(255,255,255,0.75)", mt: 1.25, mb: 3 }}>
                    You must be logged in to view your friends.
                </Typography>

                <Button
                    variant="contained"
                    onClick={onLogin}
                    sx={{
                        borderRadius: 999,
                        textTransform: "none",
                        px: 4,
                        py: 1.25,
                        bgcolor: "#9d7dff",
                        "&:hover": { bgcolor: "#7f5aff" },
                    }}
                >
                    Log In
                </Button>
            </Box>
        </Box>
    );
}
