import { Box, Typography } from "@mui/material";
import { TopNavBar } from "../components/layout/TopNavBar";

export function AdminPage() {
    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100vw",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                position: "relative",
                overflow: "hidden",
            }}
        >
            <Box
                sx={{
                    position: "absolute",
                    top: 0,
                    left: 0,
                    right: 0,
                    zIndex: 2,
                }}
            >
                <TopNavBar />
            </Box>

            <Box
                sx={{
                    width: { xs: "96vw", sm: 700, md: 900 },
                    maxWidth: "96vw",
                    borderRadius: 4,
                    p: { xs: 2.5, md: 4 },
                    bgcolor: "rgba(10, 16, 36, 0.97)",
                    boxShadow: "0 24px 60px rgba(0,0,0,0.7)",
                    position: "relative",
                }}
            >
                <Box
                    sx={{
                        position: "absolute",
                        inset: 0,
                        pointerEvents: "none",
                        opacity: 0.4,
                        background:
                            "radial-gradient(circle at 0% 0%, rgba(0,200,255,0.18), transparent 55%)," +
                            "radial-gradient(circle at 100% 100%, rgba(255,0,128,0.23), transparent 55%)",
                        zIndex: 0,
                        borderRadius: 4,
                    }}
                />

                <Box sx={{ position: "relative", zIndex: 1, color: "#fff" }}>
                    <Typography
                        variant="h4"
                        sx={{
                            fontWeight: 700,
                            letterSpacing: 0.5,
                            mb: 3,
                            color: "#7bb7ff",
                        }}
                    >
                        Game approval requests
                    </Typography>

                    <Box
                        sx={{
                            mt: 2,
                            p: 3,
                            borderRadius: 2,
                            bgcolor: "rgba(255,255,255,0.07)",
                            border: "1px solid rgba(255,255,255,0.15)",
                            color: "#cfd7ff",
                            boxShadow: "0 18px 40px rgba(0,0,0,0.5)",
                        }}
                    >
                        <Typography variant="body1">
                            This is the admin area where you will review and approve new games.
                        </Typography>
                        <Typography variant="body2" sx={{ mt: 1.5, color: "#9db3ff" }}>
                            Later you can add a list of submitted games, actions to approve or reject,
                            and filters for status, creator, and date.
                        </Typography>
                    </Box>
                </Box>
            </Box>
        </Box>
    );
}

export default AdminPage;
