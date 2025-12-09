import { useState, useContext, useMemo } from "react";
import {
    Box,
    CircularProgress,
    Typography,
    Stack,
    Button,
    TextField,
    Grid,
    Alert,
    Snackbar,
    IconButton,
    Chip
} from "@mui/material";
import PersonRoundedIcon from "@mui/icons-material/PersonRounded";
import PersonAddRoundedIcon from "@mui/icons-material/PersonAddRounded";
import PersonRemoveRoundedIcon from "@mui/icons-material/PersonRemoveRounded";
import CloseRoundedIcon from "@mui/icons-material/CloseRounded";
import CheckRoundedIcon from "@mui/icons-material/CheckRounded";
import HourglassEmptyRoundedIcon from "@mui/icons-material/HourglassEmptyRounded";
import SecurityContext from "../context/SecurityContext";
import {
    useFriends,
    usePendingRequests,
    useSendFriendRequest,
    useAcceptFriendRequest,
    useRejectFriendRequest,
    useRemoveFriend,
    useSentRequests
} from "../hooks/useFriends";
import { useSearchPlayers } from "../hooks/usePlayer";
import type { PlayerDtoWithName } from "../models/friendship";

export function FriendsPage() {
    const { isAuthenticated, loggedInUser, login } = useContext(SecurityContext);
    const playerId = loggedInUser?.id ?? null;

    const [searchQuery, setSearchQuery] = useState("");
    const [submittedQuery, setSubmittedQuery] = useState("");
    const [snackbar, setSnackbar] = useState<{
        open: boolean;
        message: string;
        severity: "success" | "error";
    }>({
        open: false,
        message: "",
        severity: "success",
    });

    const { data: friends, isLoading: loadingFriends } = useFriends(playerId);
    const { data: pendingRequests, isLoading: loadingPending } = usePendingRequests(playerId);
    const { data: sentRequests } = useSentRequests(playerId);

    const searchMutation = useSearchPlayers(submittedQuery);
    const sendFriendRequestMutation = useSendFriendRequest();
    const acceptFriendRequestMutation = useAcceptFriendRequest();
    const rejectFriendRequestMutation = useRejectFriendRequest();
    const removeFriendMutation = useRemoveFriend();

    const [localSentRequests, setLocalSentRequests] = useState<Set<string>>(new Set());

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();
        const query = searchQuery.trim();
        if (query) {
            setSubmittedQuery(query);
        }
    };

    const handleSendFriendRequest = async (toPlayerId: string) => {
        if (!playerId) return;

        try {
            setLocalSentRequests((prev) => new Set(prev).add(toPlayerId));

            await sendFriendRequestMutation.mutateAsync({ fromPlayerId: playerId, toPlayerId });
            setSnackbar({
                open: true,
                message: "Friend request sent!",
                severity: "success",
            });

            setLocalSentRequests((prev) => {
                const next = new Set(prev);
                next.delete(toPlayerId);
                return next;
            });
        } catch (error: any) {
            setLocalSentRequests((prev) => {
                const next = new Set(prev);
                next.delete(toPlayerId);
                return next;
            });

            const errorMsg = error?.response?.data?.message || "Failed to send friend request";
            setSnackbar({ open: true, message: errorMsg, severity: "error" });
        }
    };

    const handleAcceptFriendRequest = async (fromPlayerId: string) => {
        if (!playerId) return;

        try {
            await acceptFriendRequestMutation.mutateAsync({
                fromPlayerId,
                toPlayerId: playerId,
            });
            setSnackbar({
                open: true,
                message: "Friend request accepted!",
                severity: "success",
            });
        } catch (error: any) {
            const errorMsg =
                error?.response?.data?.message || "Failed to accept friend request";
            setSnackbar({ open: true, message: errorMsg, severity: "error" });
        }
    };

    const handleRejectFriendRequest = async (fromPlayerId: string) => {
        if (!playerId) return;

        try {
            await rejectFriendRequestMutation.mutateAsync({
                fromPlayerId,
                toPlayerId: playerId,
            });
            setSnackbar({
                open: true,
                message: "Friend request rejected",
                severity: "success",
            });
        } catch (error: any) {
            const errorMsg =
                error?.response?.data?.message || "Failed to reject friend request";
            setSnackbar({ open: true, message: errorMsg, severity: "error" });
        }
    };

    const handleRemoveFriend = async (friendId: string) => {
        if (!playerId) return;

        const confirmed = window.confirm("Are you sure you want to remove this friend?");
        if (!confirmed) return;

        try {
            await removeFriendMutation.mutateAsync({
                playerAId: playerId,
                playerBId: friendId,
            });
            setSnackbar({
                open: true,
                message: "Friend removed",
                severity: "success",
            });
        } catch (error: any) {
            const errorMsg =
                error?.response?.data?.message || "Failed to remove friend";
            setSnackbar({ open: true, message: errorMsg, severity: "error" });
        }
    };

    const handleCloseSnackbar = () => {
        setSnackbar((prev) => ({ ...prev, open: false }));
    };

    const getPlayerStatus = (
        playerToCheckId: string
    ): "friend" | "pending_received" | "pending_sent" | "none" => {
        if (friends?.some((f) => f.playerId === playerToCheckId)) return "friend";
        if (pendingRequests?.some((r) => r.playerId === playerToCheckId))
            return "pending_received";
        if (
            sentRequests?.some((s) => s.playerId === playerToCheckId) ||
            localSentRequests.has(playerToCheckId)
        ) {
            return "pending_sent";
        }
        return "none";
    };

    const filteredSearchResults = useMemo(
        () =>
            searchMutation.data?.filter((player: PlayerDtoWithName) => {
                if (player.playerId === playerId) return false;
                return getPlayerStatus(player.playerId) === "none";
            }) || [],
        [
            searchMutation.data,
            playerId,
            friends,
            pendingRequests,
            sentRequests,
            localSentRequests,
        ]
    );

    if (!isAuthenticated()) {
        return (
            <Box
                sx={{
                    minHeight: "100vh",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                    color: "#fff",
                    textAlign: "center",
                    p: 4,
                }}
            >
                <PersonRoundedIcon
                    sx={{ fontSize: 48, color: "#9d7dff", mb: 2 }}
                />
                <Typography variant="h5" gutterBottom sx={{ fontWeight: 600 }}>
                    Access Denied
                </Typography>
                <Typography variant="body1" sx={{ mb: 3 }}>
                    You must be logged in to view your friends.
                </Typography>
                <Button
                    variant="contained"
                    onClick={login}
                    sx={{
                        bgcolor: "#9d7dff",
                        "&:hover": { bgcolor: "#7f5aff" },
                        color: "white",
                        textTransform: "none",
                        borderRadius: 999,
                        px: 4,
                        py: 1.5,
                    }}
                >
                    Log In
                </Button>
            </Box>
        );
    }

    return (
        <Box
            sx={{
                minHeight: "100vh",
                width: "100%",
                display: "flex",
                justifyContent: "center",
                alignItems: "flex-start",
                background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                pt: { xs: 9, sm: 10 },
                pb: 4,
            }}
        >
            <Box
                sx={{
                    width: { xs: "98vw", sm: 900, md: 1000 },
                    maxWidth: "98vw",
                    borderRadius: 4,
                    p: { xs: 2, md: 4 },
                    bgcolor: "rgba(10, 16, 36, 0.97)",
                    boxShadow: "0 24px 60px rgba(0,0,0,0.7)",
                }}
            >
                <Stack spacing={4}>
                    <Box>
                        <Typography
                            variant="h4"
                            sx={{ fontWeight: 700, color: "#fff" }}
                        >
                            Your Friends
                        </Typography>
                        {friends && friends.length > 0 && (
                            <Chip
                                label={`${friends.length} friend${
                                    friends.length !== 1 ? "s" : ""
                                }`}
                                size="small"
                                sx={{
                                    mt: 1,
                                    bgcolor: "rgba(157, 125, 255, 0.2)",
                                    color: "#9d7dff",
                                }}
                            />
                        )}
                    </Box>

                    {loadingFriends ? (
                        <Stack alignItems="center" py={5}>
                            <CircularProgress sx={{ color: "#fff" }} />
                            <Typography
                                sx={{ color: "#d0d0e5", mt: 2 }}
                            >
                                Loading friends...
                            </Typography>
                        </Stack>
                    ) : friends?.length ? (
                        <Grid container spacing={2}>
                            {friends.map((friend: PlayerDtoWithName) => (
                                <Grid
                                    item
                                    xs={12}
                                    sm={6}
                                    md={4}
                                    key={friend.playerId}
                                >
                                    <Stack
                                        direction="row"
                                        alignItems="center"
                                        justifyContent="space-between"
                                        sx={{
                                            p: 2,
                                            bgcolor: "rgba(255,255,255,0.05)",
                                            borderRadius: 2,
                                            transition: "all 0.2s",
                                            "&:hover": {
                                                bgcolor: "rgba(255,255,255,0.08)",
                                            },
                                        }}
                                    >
                                        <Typography
                                            sx={{
                                                color: "#fff",
                                                fontWeight: 500,
                                            }}
                                        >
                                            {friend.username}
                                        </Typography>
                                        <IconButton
                                            size="small"
                                            onClick={() =>
                                                handleRemoveFriend(
                                                    friend.playerId
                                                )
                                            }
                                            disabled={
                                                removeFriendMutation.isPending
                                            }
                                            sx={{
                                                color: "#ff4444",
                                                "&:hover": {
                                                    bgcolor:
                                                        "rgba(255, 68, 68, 0.1)",
                                                },
                                            }}
                                        >
                                            <PersonRemoveRoundedIcon fontSize="small" />
                                        </IconButton>
                                    </Stack>
                                </Grid>
                            ))}
                        </Grid>
                    ) : (
                        <Box
                            sx={{
                                textAlign: "center",
                                py: 4,
                                color: "#d0d0e5",
                            }}
                        >
                            <Typography>
                                No friends yet. Search for players below to add
                                friends!
                            </Typography>
                        </Box>
                    )}

                    {loadingPending ? (
                        <Stack alignItems="center" py={2}>
                            <CircularProgress sx={{ color: "#fff" }} />
                        </Stack>
                    ) : pendingRequests?.length ? (
                        <>
                            <Box>
                                <Typography
                                    variant="h5"
                                    sx={{ fontWeight: 600, color: "#fff" }}
                                >
                                    Pending Requests
                                </Typography>
                                <Chip
                                    label={`${pendingRequests.length} pending`}
                                    size="small"
                                    sx={{
                                        mt: 1,
                                        bgcolor: "rgba(255, 193, 7, 0.2)",
                                        color: "#ffc107",
                                    }}
                                />
                            </Box>
                            <Grid container spacing={2}>
                                {pendingRequests.map(
                                    (request: PlayerDtoWithName) => (
                                        <Grid
                                            item
                                            xs={12}
                                            sm={6}
                                            md={4}
                                            key={request.playerId}
                                        >
                                            <Stack
                                                spacing={1}
                                                sx={{
                                                    p: 2,
                                                    bgcolor:
                                                        "rgba(255,255,255,0.05)",
                                                    borderRadius: 2,
                                                }}
                                            >
                                                <Typography
                                                    sx={{
                                                        color: "#fff",
                                                        fontWeight: 500,
                                                    }}
                                                >
                                                    {request.username}
                                                </Typography>
                                                <Stack
                                                    direction="row"
                                                    spacing={1}
                                                >
                                                    <Button
                                                        variant="contained"
                                                        size="small"
                                                        fullWidth
                                                        startIcon={
                                                            <CheckRoundedIcon />
                                                        }
                                                        onClick={() =>
                                                            handleAcceptFriendRequest(
                                                                request.playerId
                                                            )
                                                        }
                                                        disabled={
                                                            acceptFriendRequestMutation.isPending
                                                        }
                                                        sx={{
                                                            bgcolor: "#28a745",
                                                            "&:hover": {
                                                                bgcolor:
                                                                    "#218838",
                                                            },
                                                            textTransform:
                                                                "none",
                                                        }}
                                                    >
                                                        Accept
                                                    </Button>
                                                    <Button
                                                        variant="outlined"
                                                        size="small"
                                                        fullWidth
                                                        startIcon={
                                                            <CloseRoundedIcon />
                                                        }
                                                        onClick={() =>
                                                            handleRejectFriendRequest(
                                                                request.playerId
                                                            )
                                                        }
                                                        disabled={
                                                            rejectFriendRequestMutation.isPending
                                                        }
                                                        sx={{
                                                            borderColor:
                                                                "#dc3545",
                                                            color: "#dc3545",
                                                            "&:hover": {
                                                                borderColor:
                                                                    "#c82333",
                                                                bgcolor:
                                                                    "rgba(220, 53, 69, 0.1)",
                                                            },
                                                            textTransform:
                                                                "none",
                                                        }}
                                                    >
                                                        Reject
                                                    </Button>
                                                </Stack>
                                            </Stack>
                                        </Grid>
                                    )
                                )}
                            </Grid>
                        </>
                    ) : null}

                    {sentRequests?.length ? (
                        <>
                            <Box>
                                <Typography
                                    variant="h5"
                                    sx={{ fontWeight: 600, color: "#fff" }}
                                >
                                    Sent Requests
                                </Typography>
                                <Chip
                                    label={`${sentRequests.length} sent`}
                                    size="small"
                                    sx={{
                                        mt: 1,
                                        bgcolor:
                                            "rgba(100, 149, 237, 0.2)",
                                        color: "#6495ed",
                                    }}
                                />
                            </Box>
                            <Grid container spacing={2}>
                                {sentRequests.map(
                                    (sent: PlayerDtoWithName) => (
                                        <Grid
                                            item
                                            xs={12}
                                            sm={6}
                                            md={4}
                                            key={sent.playerId}
                                        >
                                            <Stack
                                                direction="row"
                                                alignItems="center"
                                                justifyContent="space-between"
                                                sx={{
                                                    p: 2,
                                                    bgcolor:
                                                        "rgba(255,255,255,0.05)",
                                                    borderRadius: 2,
                                                }}
                                            >
                                                <Typography
                                                    sx={{
                                                        color: "#fff",
                                                        fontWeight: 500,
                                                    }}
                                                >
                                                    {sent.username}
                                                </Typography>
                                                <Chip
                                                    icon={
                                                        <HourglassEmptyRoundedIcon />
                                                    }
                                                    label="Pending"
                                                    size="small"
                                                    sx={{
                                                        bgcolor:
                                                            "rgba(100, 149, 237, 0.2)",
                                                        color: "#6495ed",
                                                    }}
                                                />
                                            </Stack>
                                        </Grid>
                                    )
                                )}
                            </Grid>
                        </>
                    ) : null}

                    <Box>
                        <Typography
                            variant="h5"
                            sx={{ fontWeight: 600, color: "#fff", mb: 2 }}
                        >
                            Find Players
                        </Typography>
                        <form onSubmit={handleSearch}>
                            <Stack
                                direction={{ xs: "column", sm: "row" }}
                                spacing={2}
                                mb={2}
                            >
                                <TextField
                                    value={searchQuery}
                                    onChange={(e) =>
                                        setSearchQuery(e.target.value)
                                    }
                                    placeholder="Enter username..."
                                    fullWidth
                                    sx={{
                                        bgcolor: "#fff",
                                        borderRadius: 2,
                                        "& .MuiOutlinedInput-root": {
                                            "& fieldset": {
                                                borderColor: "transparent",
                                            },
                                        },
                                    }}
                                />
                                <Button
                                    type="submit"
                                    variant="contained"
                                    disabled={!searchQuery.trim()}
                                    sx={{
                                        bgcolor: "#9d7dff",
                                        "&:hover": { bgcolor: "#7f5aff" },
                                        color: "#fff",
                                        textTransform: "none",
                                        px: 4,
                                        minWidth: { sm: 120 },
                                    }}
                                >
                                    Search
                                </Button>
                            </Stack>
                        </form>

                        {searchMutation.isLoading && (
                            <Stack alignItems="center" py={2}>
                                <CircularProgress sx={{ color: "#fff" }} />
                                <Typography
                                    sx={{ color: "#d0d0e5", mt: 1 }}
                                >
                                    Searching players...
                                </Typography>
                            </Stack>
                        )}

                        {submittedQuery &&
                            !searchMutation.isLoading &&
                            filteredSearchResults.length === 0 && (
                                <Box
                                    sx={{
                                        textAlign: "center",
                                        py: 3,
                                        color: "#d0d0e5",
                                    }}
                                >
                                    <Typography>
                                        {searchMutation.data?.length
                                            ? "All matching players are already your friends or have pending requests"
                                            : `No players found matching "${submittedQuery}"`}
                                    </Typography>
                                </Box>
                            )}

                        <Grid container spacing={2}>
                            {filteredSearchResults.map(
                                (player: PlayerDtoWithName) => (
                                    <Grid
                                        item
                                        xs={12}
                                        sm={6}
                                        md={4}
                                        key={player.playerId}
                                    >
                                        <Stack
                                            direction="row"
                                            alignItems="center"
                                            justifyContent="space-between"
                                            sx={{
                                                p: 2,
                                                bgcolor:
                                                    "rgba(255,255,255,0.05)",
                                                borderRadius: 2,
                                                transition: "all 0.2s",
                                                "&:hover": {
                                                    bgcolor:
                                                        "rgba(255,255,255,0.08)",
                                                },
                                            }}
                                        >
                                            <Typography
                                                sx={{
                                                    color: "#fff",
                                                    fontWeight: 500,
                                                }}
                                            >
                                                {player.username}
                                            </Typography>
                                            <Button
                                                variant="contained"
                                                size="small"
                                                startIcon={
                                                    <PersonAddRoundedIcon />
                                                }
                                                onClick={() =>
                                                    handleSendFriendRequest(
                                                        player.playerId
                                                    )
                                                }
                                                disabled={
                                                    sendFriendRequestMutation.isPending
                                                }
                                                sx={{
                                                    bgcolor: "#28a745",
                                                    "&:hover": {
                                                        bgcolor: "#218838",
                                                    },
                                                    textTransform: "none",
                                                }}
                                            >
                                                Add
                                            </Button>
                                        </Stack>
                                    </Grid>
                                )
                            )}
                        </Grid>
                    </Box>
                </Stack>
            </Box>

            <Snackbar
                open={snackbar.open}
                autoHideDuration={4000}
                onClose={handleCloseSnackbar}
                anchorOrigin={{
                    vertical: "bottom",
                    horizontal: "center",
                }}
            >
                <Alert
                    onClose={handleCloseSnackbar}
                    severity={snackbar.severity}
                    sx={{ width: "100%" }}
                >
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </Box>
    );
}
