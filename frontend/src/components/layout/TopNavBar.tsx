import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Button,
    IconButton,
    Avatar,
    Menu,
    MenuItem,
} from "@mui/material";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";
import useMediaQuery from "@mui/material/useMediaQuery";
import { useContext, useState } from "react";
import SecurityContext from "../../context/SecurityContext";
import { useNavigate, useLocation } from "react-router-dom";
import SportsEsportsRoundedIcon from "@mui/icons-material/SportsEsportsRounded";

const FONT_FAMILY =
    '"Poppins", system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif';

export function TopNavBar() {
    const { isAuthenticated, loggedInUser, login, logout } =
        useContext(SecurityContext);
    const navigate = useNavigate();
    const location = useLocation();
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [mobileNavOpen, setMobileNavOpen] = useState(false);

    const roles = loggedInUser?.roles ?? [];
    const isAdmin = roles.includes("admin");

    const isMdUp = useMediaQuery("(min-width:960px)");

    const handleNavClick = (path: string) => {
        setMobileNavOpen(false);
        navigate(path);
    };

    const handleBrandClick = () => {
        setMobileNavOpen(false);
        navigate("/public");
    };

    const handleUserClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => setAnchorEl(null);

    const handleAdminClick = () => {
        handleMenuClose();
        navigate("/admin");
    };

    const handleLogoutClick = () => {
        handleMenuClose();
        logout?.();
    };

    const activeStyle = (path: string) =>
        location.pathname.startsWith(path)
            ? {
                color: "#f9fafb",
                fontWeight: 600,
                "&::after": {
                    content: '""',
                    display: "block",
                    height: 2,
                    borderRadius: 999,
                    background:
                        "linear-gradient(90deg, #6366f1, #22c55e, #facc15)",
                    mt: 0.5,
                },
            }
            : {
                color: "#cbd5f5",
                "&:hover": {
                    color: "#ffffff",
                },
            };

    const initials =
        loggedInUser?.name
            ?.split(" ")
            .map((p) => p[0])
            .join("") || "U";

    const commonButtonSx = {
        textTransform: "none",
        fontSize: 14,
        fontWeight: 500,
        minWidth: "auto",
        px: 1,
        fontFamily: FONT_FAMILY,
    } as const;

    return (
        <AppBar
            position="fixed"
            elevation={0}
            sx={{
                top: 0,
                left: 0,
                right: 0,
                background:
                    "linear-gradient(120deg, rgba(15,23,42,0.95), rgba(15,23,42,0.88))",
                backdropFilter: "blur(16px)",
                borderBottom: "1px solid rgba(148,163,184,0.25)",
                zIndex: (theme) => theme.zIndex.drawer + 1,
                boxShadow: "0 12px 40px rgba(15,23,42,0.65)",
                fontFamily: FONT_FAMILY,
            }}
        >
            <Toolbar
                sx={{
                    px: { xs: 2, md: 6 },
                    gap: 2,
                    minHeight: 64,
                    fontFamily: FONT_FAMILY,
                }}
            >
                {/* Brand / Logo */}
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        gap: 1,
                        cursor: "pointer",
                    }}
                    onClick={handleBrandClick}
                >
                    <Box
                        sx={{
                            width: 30,
                            height: 30,
                            borderRadius: 2,
                            background:
                                "radial-gradient(circle at 0% 0%, #6366f1, transparent 60%), " +
                                "radial-gradient(circle at 100% 100%, #22c55e, transparent 60%)",
                            boxShadow: "0 0 16px rgba(129,140,248,0.7)",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            color: "#fff",
                        }}
                    >
                        <SportsEsportsRoundedIcon sx={{ fontSize: 18 }} />
                    </Box>

                    <Typography
                        variant="h6"
                        sx={{
                            fontWeight: 700,
                            letterSpacing: 0.5,
                            color: "#f9fafb",
                            fontFamily: FONT_FAMILY,
                        }}
                    >
                        BanditGames
                    </Typography>
                </Box>

                {isAuthenticated() && isMdUp && (
                    <Box
                        sx={{
                            display: "flex",
                            alignItems: "center",
                            gap: 2.5,
                            flexGrow: 1,
                            ml: 4,
                            fontSize: 14,
                        }}
                    >
                        {isAdmin ? (
                            <Button
                                onClick={() => handleNavClick("/admin")}
                                sx={{
                                    ...commonButtonSx,
                                    ...activeStyle("/admin"),
                                }}
                            >
                                Admin
                            </Button>
                        ) : (
                            <>
                                <Button
                                    onClick={() => handleNavClick("/games")}
                                    sx={{
                                        ...commonButtonSx,
                                        ...activeStyle("/games"),
                                    }}
                                >
                                    Games
                                </Button>

                                <Button
                                    onClick={() => handleNavClick("/favorites")}
                                    sx={{
                                        ...commonButtonSx,
                                        ...activeStyle("/favorites"),
                                    }}
                                >
                                    Favorites
                                </Button>

                                <Button
                                    onClick={() => handleNavClick("/friends")}
                                    sx={{
                                        ...commonButtonSx,
                                        ...activeStyle("/friends"),
                                    }}
                                >
                                    Friends
                                </Button>

                                <Button
                                    onClick={() => handleNavClick("/lobby")}
                                    sx={{
                                        ...commonButtonSx,
                                        ...activeStyle("/lobby"),
                                    }}
                                >
                                    Lobby
                                </Button>
                            </>
                        )}
                    </Box>
                )}

                {(!isAuthenticated() || !isMdUp) && (
                    <Box sx={{ flexGrow: 1 }} />
                )}

                {isAuthenticated() ? (
                    <Box
                        sx={{
                            display: "flex",
                            alignItems: "center",
                            ml: 2,
                            gap: 1,
                        }}
                    >
                        {isMdUp && (
                            <IconButton size="small" sx={{ color: "#e5e7eb" }}>
                                <NotificationsNoneIcon fontSize="small" />
                            </IconButton>
                        )}

                        <Button
                            onClick={handleUserClick}
                            sx={{
                                textTransform: "none",
                                color: "#f9fafb",
                                display: "flex",
                                alignItems: "center",
                                gap: 1,
                                px: 0,
                                fontFamily: FONT_FAMILY,
                            }}
                        >
                            <Avatar
                                sx={{
                                    width: 30,
                                    height: 30,
                                    fontSize: 13,
                                    bgcolor:
                                        "linear-gradient(135deg,#6366f1,#22c55e)",
                                }}
                            >
                                {initials}
                            </Avatar>
                            {isMdUp && (
                                <Typography
                                    variant="body2"
                                    sx={{ fontFamily: FONT_FAMILY }}
                                >
                                    {loggedInUser?.name || "User"}
                                </Typography>
                            )}
                        </Button>

                        <Menu
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleMenuClose}
                            anchorOrigin={{
                                vertical: "bottom",
                                horizontal: "right",
                            }}
                            transformOrigin={{
                                vertical: "top",
                                horizontal: "right",
                            }}
                        >
                            {isAdmin && (
                                <MenuItem onClick={handleAdminClick}>
                                    Go to Admin page
                                </MenuItem>
                            )}
                            <MenuItem onClick={handleLogoutClick}>
                                Logout
                            </MenuItem>
                        </Menu>

                        {!isMdUp && (
                            <IconButton
                                sx={{ color: "#e5e7eb", ml: 1 }}
                                onClick={() =>
                                    setMobileNavOpen((prev) => !prev)
                                }
                            >
                                {mobileNavOpen ? <CloseIcon /> : <MenuIcon />}
                            </IconButton>
                        )}
                    </Box>
                ) : (
                    <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                        {!isMdUp && (
                            <IconButton
                                sx={{ color: "#e5e7eb" }}
                                onClick={() =>
                                    setMobileNavOpen((prev) => !prev)
                                }
                            >
                                {mobileNavOpen ? <CloseIcon /> : <MenuIcon />}
                            </IconButton>
                        )}
                        <Button
                            variant="outlined"
                            size="small"
                            onClick={login}
                            sx={{
                                textTransform: "none",
                                borderRadius: 999,
                                px: 2.5,
                                fontFamily: FONT_FAMILY,
                                borderColor: "rgba(148,163,184,0.9)",
                                color: "#e5e7eb",
                                "&:hover": {
                                    borderColor: "#e5e7eb",
                                    background:
                                        "linear-gradient(120deg, rgba(129,140,248,0.2), rgba(45,212,191,0.2))",
                                },
                            }}
                        >
                            Login
                        </Button>
                    </Box>
                )}
            </Toolbar>

            {!isMdUp && mobileNavOpen && isAuthenticated() && (
                <Box
                    sx={{
                        px: 2,
                        pb: 2,
                        display: "flex",
                        flexDirection: "column",
                        gap: 1,
                        bgcolor: "rgba(15,23,42,0.97)",
                        borderTop: "1px solid rgba(148,163,184,0.3)",
                    }}
                >
                    {isAdmin ? (
                        <Button
                            onClick={() => handleNavClick("/admin")}
                            sx={{
                                ...commonButtonSx,
                                justifyContent: "flex-start",
                                mt: 1,
                                ...activeStyle("/admin"),
                            }}
                        >
                            Admin
                        </Button>
                    ) : (
                        <>
                            <Button
                                onClick={() => handleNavClick("/games")}
                                sx={{
                                    ...commonButtonSx,
                                    justifyContent: "flex-start",
                                    mt: 1,
                                    ...activeStyle("/games"),
                                }}
                            >
                                Games
                            </Button>
                            <Button
                                onClick={() => handleNavClick("/favorites")}
                                sx={{
                                    ...commonButtonSx,
                                    justifyContent: "flex-start",
                                    ...activeStyle("/favorites"),
                                }}
                            >
                                Favorites
                            </Button>
                            <Button
                                onClick={() => handleNavClick("/friends")}
                                sx={{
                                    ...commonButtonSx,
                                    justifyContent: "flex-start",
                                    ...activeStyle("/friends"),
                                }}
                            >
                                Friends
                            </Button>
                            <Button
                                onClick={() => handleNavClick("/lobby")}
                                sx={{
                                    ...commonButtonSx,
                                    justifyContent: "flex-start",
                                    ...activeStyle("/lobby"),
                                }}
                            >
                                Lobby
                            </Button>
                        </>
                    )}
                </Box>
            )}
        </AppBar>
    );
}
