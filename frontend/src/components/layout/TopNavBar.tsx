import {
    AppBar,
    Toolbar,
    Typography,
    Box,
    Button,
    IconButton,
    InputBase,
    Avatar,
    Menu,
    MenuItem,
} from "@mui/material";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import SearchIcon from "@mui/icons-material/Search";
import { useContext, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import SecurityContext from "../../context/SecurityContext";

export function TopNavBar() {
    const { isAuthenticated, loggedInUser, login, logout } = useContext(SecurityContext);
    const location = useLocation();
    const navigate = useNavigate();
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

    const roles = loggedInUser?.roles ?? [];
    const isAdmin = roles.includes("admin");
    const isLoggedIn = isAuthenticated();

    const handleNavClick = (path: string) => {
        navigate(path);
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
            ? { borderBottom: "2px solid black", fontWeight: 600 }
            : { color: "#555" };

    const initials =
        loggedInUser?.name
            ?.split(" ")
            .map((p) => p[0])
            .join("") || "U";

    return (
        <AppBar
            position="static"
            elevation={0}
            sx={{
                bgcolor: "white",
                color: "black",
                borderBottom: "1px solid #e5e5e5",
            }}
        >
            <Toolbar sx={{ px: { xs: 2, md: 6 }, gap: 3 }}>
                {/* Brand */}
                <Typography
                    variant="h6"
                    sx={{ fontWeight: 700, letterSpacing: 0.3, cursor: "pointer" }}
                    onClick={() => handleNavClick("/public")}
                >
                    BanditGames
                </Typography>

                {/* Nav buttons – vary by auth / role */}
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        gap: 3,
                        flexGrow: 1,
                        ml: 4,
                        fontSize: 14,
                    }}
                >
                    {/* Home is always visible */}
                    <Button
                        onClick={() => handleNavClick("/public")}
                        sx={{ textTransform: "none", minWidth: "auto", ...activeStyle("/public") }}
                    >
                        Home
                    </Button>

                    {/* Logged-in admin: ONLY Home + Admin */}
                    {isLoggedIn && isAdmin && (
                        <Button
                            onClick={() => handleNavClick("/admin")}
                            sx={{ textTransform: "none", minWidth: "auto", ...activeStyle("/admin") }}
                        >
                            Admin
                        </Button>
                    )}

                    {/* Logged-in non-admin (player): Home + all original buttons except Admin */}
                    {isLoggedIn && !isAdmin && (
                        <>
                            <Button
                                onClick={() => handleNavClick("/games")}
                                sx={{ textTransform: "none", minWidth: "auto", ...activeStyle("/games") }}
                            >
                                Games
                            </Button>
                            <Button
                                disabled
                                sx={{ textTransform: "none", minWidth: "auto", color: "#aaa" }}
                            >
                                Friends
                            </Button>
                            <Button
                                disabled
                                sx={{ textTransform: "none", minWidth: "auto", color: "#aaa" }}
                            >
                                Achievements
                            </Button>
                            <Button
                                disabled
                                sx={{ textTransform: "none", minWidth: "auto", color: "#aaa" }}
                            >
                                Lobby
                            </Button>
                        </>
                    )}

                    {/* Unlogged user: nothing extra (just Home above) */}
                </Box>

                {/* Search – leave as-is */}
                <Box
                    sx={{
                        display: { xs: "none", md: "flex" },
                        alignItems: "center",
                        bgcolor: "#fafafa",
                        borderRadius: 2,
                        px: 1.5,
                        py: 0.5,
                        border: "1px solid #e0e0e0",
                        minWidth: 220,
                    }}
                >
                    <InputBase
                        placeholder="Search..."
                        sx={{ fontSize: 14, flexGrow: 1 }}
                        disabled
                    />
                    <SearchIcon sx={{ fontSize: 18, color: "#9e9e9e" }} />
                </Box>

                {/* Right side: user or login */}
                {isLoggedIn ? (
                    <Box sx={{ display: "flex", alignItems: "center", ml: 3, gap: 1.5 }}>
                        <IconButton size="small">
                            <NotificationsNoneIcon fontSize="small" />
                        </IconButton>
                        <Button
                            onClick={handleUserClick}
                            sx={{
                                textTransform: "none",
                                color: "black",
                                display: "flex",
                                alignItems: "center",
                                gap: 1,
                                px: 0,
                            }}
                        >
                            <Avatar
                                sx={{
                                    width: 28,
                                    height: 28,
                                    fontSize: 13,
                                    bgcolor: "#111827",
                                }}
                            >
                                {initials}
                            </Avatar>
                            <Typography variant="body2">
                                {loggedInUser?.name || "User"}
                            </Typography>
                        </Button>

                        <Menu
                            anchorEl={anchorEl}
                            open={Boolean(anchorEl)}
                            onClose={handleMenuClose}
                            anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
                            transformOrigin={{ vertical: "top", horizontal: "right" }}
                        >
                            {isAdmin && (
                                <MenuItem onClick={handleAdminClick}>Go to Admin page</MenuItem>
                            )}
                            <MenuItem onClick={handleLogoutClick}>Logout</MenuItem>
                        </Menu>
                    </Box>
                ) : (
                    <Button
                        variant="outlined"
                        size="small"
                        onClick={login}
                        sx={{
                            ml: 3,
                            textTransform: "none",
                            borderRadius: 999,
                            px: 3,
                        }}
                    >
                        Login
                    </Button>
                )}
            </Toolbar>
        </AppBar>
    );
}
