import React, { useContext, useRef } from "react";
import {
    Box,
    Stack,
    Typography,
    Button,
    Grid,
    Chip,
    Divider,
} from "@mui/material";
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";
import FlashOnRoundedIcon from "@mui/icons-material/FlashOnRounded";
import GroupsRoundedIcon from "@mui/icons-material/GroupsRounded";
import SmartToyRoundedIcon from "@mui/icons-material/SmartToyRounded";
import EmojiEventsRoundedIcon from "@mui/icons-material/EmojiEventsRounded";
import SecurityContext from "../context/SecurityContext";
import Footer from "../components/layout/Footer";



export function PublicPage() {
    const { login, isAuthenticated } = useContext(SecurityContext);

    const featuresRef = useRef<HTMLDivElement | null>(null);

    const handleSignInClick = () => {
        login();
    };

    const handleLearnMoreClick = () => {
        if (featuresRef.current) {
            const top = featuresRef.current.getBoundingClientRect().top + window.pageYOffset;
            const offset = -90;

            window.scrollTo({
                top: top + offset,
                behavior: "smooth",
            });
        }
    };


    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100vw",
                background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                overflowX: "hidden",
                overflowY: "auto",
                py: { xs: 6, md: 8 },
                px: { xs: 2, md: 4 },
            }}
        >
            <Box
                sx={{
                    position: "fixed",
                    inset: 0,
                    pointerEvents: "none",
                    opacity: 0.4,
                    background:
                        "radial-gradient(circle at 0% 0%, rgba(0,200,255,0.18), transparent 55%)," +
                        "radial-gradient(circle at 100% 100%, rgba(255,0,128,0.23), transparent 55%)",
                    zIndex: 0,
                }}
            />

            <Stack
                spacing={6}
                sx={{ position: "relative", zIndex: 1, maxWidth: "1600px", mx: "auto" }}
            >
                <Box
                    sx={{
                        width: "100%",
                        borderRadius: 4,
                        p: { xs: 3, md: 6 },
                        background: "linear-gradient(135deg, #2e1a4e 0%, #0a1024 100%)",
                        border: "1px solid rgba(157,125,255,0.35)",
                        boxShadow: "0 0 22px rgba(157,125,255,0.18)",

                    }}
                >
                    <Grid container spacing={6} alignItems="center">
                        <Grid item xs={12} md={7}>
                            <Stack spacing={3}>
                                <Stack direction="row" spacing={1.5} alignItems="center">
                                    <Box
                                        sx={{
                                            width: 40,
                                            height: 40,
                                            borderRadius: "50%",
                                            display: "flex",
                                            alignItems: "center",
                                            justifyContent: "center",
                                            bgcolor: "rgba(157,125,255,0.15)",
                                            boxShadow: "0 0 12px rgba(157,125,255,0.6)",
                                        }}
                                    >
                                        <SportsEsportsRoundedIcon
                                            sx={{ fontSize: 24, color: "#9d7dff" }}
                                        />
                                    </Box>
                                    <Typography
                                        variant="overline"
                                        sx={{
                                            letterSpacing: 2,
                                            color: "#9d7dff",
                                            fontWeight: 700,
                                        }}
                                    >
                                        BANDITGAMES
                                    </Typography>
                                </Stack>

                                <Typography
                                    variant="h2"
                                    sx={{
                                        fontWeight: 800,
                                        color: "#ffffff",
                                        maxWidth: 900,
                                        lineHeight: 1.1,
                                        fontSize: { xs: "2rem", md: "3.2rem" },
                                    }}
                                >
                                    Board Games with{" "}
                                    <Box component="span" sx={{ color: "#fbbf24" }}>
                                        AI Bandits
                                    </Box>{" "}
                                    and Friends — Online.
                                </Typography>

                                <Typography
                                    variant="body1"
                                    sx={{
                                        color: "#d0d0e5",
                                        maxWidth: 700,
                                        fontSize: { xs: 16, md: 18 },
                                        lineHeight: 1.6,
                                    }}
                                >
                                    Play classic board games online with smart AI opponents,
                                    jump into lobbies with friends, and keep all your games in
                                    one place — from casual matches to serious rematches.
                                </Typography>

                                <Stack direction={{ xs: "column", sm: "row" }} spacing={2}>
                                    <Button
                                        variant="contained"
                                        onClick={handleSignInClick}
                                        disabled={isAuthenticated()}
                                        sx={{
                                            bgcolor: isAuthenticated() ? "rgba(255,255,255,0.22)" : "#9d7dff",
                                            color: "#ffffff",
                                            "&:hover": !isAuthenticated() && { bgcolor: "#7f5aff" },

                                            "&.Mui-disabled": {
                                                color: "#ffffff !important",
                                                bgcolor: "rgba(255,255,255,0.22) !important",
                                            },

                                            textTransform: "none",
                                            fontWeight: 800,
                                            borderRadius: 999,
                                            px: 5,
                                            py: 1.6,
                                            fontSize: 18,
                                            cursor: isAuthenticated() ? "not-allowed" : "pointer",
                                            opacity: 1,
                                            border: isAuthenticated() ? "2px solid rgba(255,255,255,0.65)" : "none",
                                            boxShadow: isAuthenticated()
                                                ? "0 0 12px rgba(255,255,255,0.35)"
                                                : "0px 0px 6px rgba(0,0,0,0.2)",
                                        }}
                                    >
                                        {isAuthenticated() ? "Already Signed In" : "Sign In to Play"}
                                    </Button>




                                    <Button
                                        variant="outlined"
                                        onClick={handleLearnMoreClick}
                                        sx={{
                                            borderColor: "rgba(255,255,255,0.5)",
                                            color: "#ffffff",
                                            "&:hover": {
                                                borderColor: "#ffffff",
                                                bgcolor: "rgba(255,255,255,0.06)",
                                            },
                                            textTransform: "none",
                                            fontWeight: 600,
                                            borderRadius: 999,
                                            px: 4,
                                            py: 1.5,
                                            fontSize: 16,
                                        }}
                                    >
                                        Learn More
                                    </Button>
                                </Stack>

                            </Stack>
                        </Grid>

                        <Grid item xs={12} md={5}>
                            <Stack spacing={3}>
                                <InfoCard
                                    icon={<FlashOnRoundedIcon sx={{ fontSize: 20 }} />}
                                    title="Jump back into games"
                                    text="When you sign in, you’ll see your current games and recent matches so you can continue exactly where you left off."
                                />
                                <InfoCard
                                    icon={<GroupsRoundedIcon sx={{ fontSize: 20 }} />}
                                    title="See who’s online"
                                    text="Once logged in, you can see which friends are around and open or join lobbies together."
                                />
                            </Stack>
                        </Grid>
                    </Grid>
                </Box>

                <Box
                    ref={featuresRef}
                    sx={{
                        width: "100%",
                        borderRadius: 4,
                        p: { xs: 3, md: 5 },
                        background: "linear-gradient(135deg, #0a223b 0%, #0a1024 100%)",
                        border: "1px solid rgba(140,230,255,0.3)",
                        boxShadow: "0 0 18px rgba(140,230,255,0.16)",

                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        textAlign: "center",
                    }}
                >
                    <Typography
                        variant="h4"
                        sx={{ color: "#ffffff", fontWeight: 800, mb: 3 }}
                    >
                        What You Can Do
                    </Typography>

                    <Grid
                        container
                        spacing={4}
                        justifyContent="center"
                        alignItems="stretch"
                    >
                        <Grid item xs={12} md={4}>
                            <FeatureCard
                                icon={<SmartToyRoundedIcon sx={{ fontSize: 26 }} />}
                                title="Play AI opponents"
                                description="Pick an AI difficulty that matches your mood — Easy, Medium, or Hard — and play instantly even if no one else is online."
                            />
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <FeatureCard
                                icon={<GroupsRoundedIcon sx={{ fontSize: 26 }} />}
                                title="Create and join lobbies"
                                description="Open a lobby for friends, or join a public one that matches the game you want to play."
                            />
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <FeatureCard
                                icon={<EmojiEventsRoundedIcon sx={{ fontSize: 26 }} />}
                                title="Track your results"
                                description="See how often you win, which games you play the most, and how you perform against AI and human players."
                            />
                        </Grid>
                    </Grid>
                </Box>


                <Box
                    sx={{
                        width: "100%",
                        borderRadius: 4,
                        p: { xs: 3, md: 5 },
                        background: "linear-gradient(135deg, #003b32 0%, #0a1024 100%)",
                        border: "1px solid rgba(0,255,195,0.25)",
                        boxShadow: "0 0 25px rgba(0,255,195,0.18)",


                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        textAlign: "center",
                    }}
                >
                    <Typography
                        variant="h4"
                        sx={{ color: "#ffffff", fontWeight: 800, mb: 3 }}
                    >
                        How It Works
                    </Typography>

                    <Grid
                        container
                        spacing={4}

                        justifyContent="center"
                        alignItems="stretch"
                    >
                        <Grid item xs={12} md={4}>
                            <StepCard
                                step="01"
                                title="Sign in"
                                text="Use your account to unlock lobbies, friends, and full gameplay."
                            />
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <StepCard
                                step="02"
                                title="Open or join a lobby"
                                text="Create a lobby for a specific game or join an existing one that’s open."
                            />
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <StepCard
                                step="03"
                                title="Play & come back later"
                                text="Games are tied to your account, add them to your favorites and come play later."
                            />
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <StepCard
                                step="04"
                                title="Ask"
                                text="Our chatbot is here for you at any time, don't be too shy and ask you silly question!"
                            />
                        </Grid>
                    </Grid>

                    <Divider sx={{ my: 4, width: "100%" }} />

                    <Typography variant="body2" sx={{ color: "#a8b3cc", maxWidth: 600 }}>
                        You can stay on this page and explore without signing in. Whenever you’re ready
                        to play for real, just hit <strong>“Sign In to Play”</strong>.
                    </Typography>
                </Box>

                <Footer />
            </Stack>
        </Box>
    );
}


function InfoCard({
                      icon,
                      title,
                      text,
                  }: {
    icon: React.ReactNode;
    title: string;
    text: string;
}) {
    return (
        <Box
            sx={{
                p: 2.5,
                borderRadius: 3,
                bgcolor: "rgba(15,23,42,0.85)",
                border: "1px solid rgba(148,163,184,0.4)",
            }}
        >
            <Stack spacing={1}>
                <Stack direction="row" spacing={1} alignItems="center">
                    {icon}
                    <Typography
                        variant="caption"
                        sx={{ color: "#9ca3af", fontWeight: 600 }}
                    >
                        {title}
                    </Typography>
                </Stack>
                <Typography variant="body2" sx={{ color: "#e5e7eb" }}>
                    {text}
                </Typography>
            </Stack>
        </Box>
    );
}

function FeatureCard({
                         icon,
                         title,
                         description,
                     }: {
    icon: React.ReactNode;
    title: string;
    description: string;
}) {
    return (
        <Box
            sx={{
                p: 3,
                borderRadius: 3,
                bgcolor: "rgba(15,23,42,0.9)",
                border: "1px solid rgba(148,163,184,0.4)",
                transition: "transform 0.2s ease",
                "&:hover": { transform: "translateY(-4px)" },
            }}
        >
            <Stack spacing={1.5}>
                <Box sx={{ color: "#fff" }}>{icon}</Box>
                <Typography sx={{ color: "#ffffff", fontWeight: 700 }}>
                    {title}
                </Typography>
                <Typography variant="body2" sx={{ color: "#d0d0e5" }}>
                    {description}
                </Typography>
            </Stack>
        </Box>
    );
}

function StepCard({
                      step,
                      title,
                      text,
                  }: {
    step: string;
    title: string;
    text: string;
}) {
    return (
        <Box
            sx={{
                p: 3,
                borderRadius: 3,
                bgcolor: "rgba(15,23,42,0.9)",
                border: "1px solid rgba(148,163,184,0.4)",
            }}
        >
            <Chip
                label={step}
                size="small"
                sx={{
                    bgcolor: "rgba(157,125,255,0.2)",
                    color: "#c7d2fe",
                    fontWeight: 700,
                    borderRadius: 999,
                }}
            />
            <Typography sx={{ mt: 1.5, color: "#ffffff", fontWeight: 700 }}>
                {title}
            </Typography>
            <Typography variant="body2" sx={{ mt: 0.5, color: "#d0d0e5" }}>
                {text}
            </Typography>
        </Box>
    );
}



export default PublicPage;
