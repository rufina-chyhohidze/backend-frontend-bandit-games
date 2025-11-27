import { useContext } from "react";
import {
    Box,
    Stack,
    Typography,
} from "@mui/material";
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";
import SecurityContext from "../context/SecurityContext";
import { TopNavBar } from "../components/layout/TopNavBar";

export function PublicPage() {
    const {login} = useContext(SecurityContext);

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
                    width: { xs: "98vw", sm: 700, md: 900 },
                    maxWidth: "98vw",
                    borderRadius: 4,
                    p: { xs: 2, md: 4 },
                    bgcolor: "rgba(10, 16, 36, 0.97)",
                    boxShadow: "0 24px 60px rgba(0,0,0,0.7)",
                    position: "relative",
                    zIndex: 1,
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

                <Stack
                    spacing={3}
                    alignItems="center"
                    sx={{ textAlign: "center", position: "relative", zIndex: 1 }}
                >
                    <Stack direction="row" alignItems="center" spacing={1}>
                        <SportsEsportsRoundedIcon sx={{ fontSize: 32, color: "#9d7dff" }} />
                        <Typography
                            variant="h4"
                            component="h1"
                            sx={{
                                fontWeight: 700,
                                letterSpacing: 0.5,
                                color: "#ffffff",
                            }}
                        >
                            Welcome to BanditGames
                        </Typography>
                    </Stack>

                    <Typography
                        variant="body1"
                        sx={{
                            color: "#d0d0e5",
                            mt: 1,
                            maxWidth: 600,
                        }}
                    >
                        The ultimate board game platform with AI-powered gameplay.
                        Play classic board games online, challenge AI opponents,
                        and track your achievements in one place.
                    </Typography>

                    <Stack spacing={0.5} sx={{ color: "#c9d3ff", fontSize: 14 }}>
                        <Typography>✓ Play classic board games online</Typography>
                        <Typography>✓ Challenge AI opponents of varying difficulty</Typography>
                        <Typography>✓ Connect with friends and track achievements</Typography>
                        <Typography>✓ Get insights and improve your gameplay</Typography>
                    </Stack>
                </Stack>
            </Box>
        </Box>
    );
}

export default PublicPage;
