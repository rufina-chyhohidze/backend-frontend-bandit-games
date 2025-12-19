import { Box, Typography } from "@mui/material";

export function Footer() {
    return (
        <Box
            component="footer"
            sx={{
                width: "100%",
                mt: 6,
                py: 3,
                px: 2,
                textAlign: "center",
                borderRadius: 3,
                bgcolor: "rgba(255,255,255,0.03)",
                borderTop: "1px solid rgba(255,255,255,0.06)",
            }}
        >
            <Typography
                variant="caption"
                sx={{
                    color: "rgba(255,255,255,0.6)",
                    fontSize: 13,
                    display: "block",
                }}
            >
                © {new Date().getFullYear()} BanditGames
            </Typography>

            <Typography
                variant="caption"
                sx={{
                    color: "rgba(255,255,255,0.38)",
                    fontSize: 12,
                    mt: 0.5,
                    display: "block",
                }}
            >
                Board games, AI opponents, and online play — built for fun.
            </Typography>
        </Box>
    );
}

export default Footer;
