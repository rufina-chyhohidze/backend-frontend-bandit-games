// pages/FriendsPage.tsx
import { useState, useContext } from "react";
import { Box, CircularProgress, Typography, Stack, Button, TextField, Grid } from "@mui/material";
import PersonRoundedIcon from "@mui/icons-material/PersonRounded";
import PersonAddRoundedIcon from "@mui/icons-material/PersonAddRounded";
import SecurityContext from "../context/SecurityContext";
import { useFriends, usePendingRequests, useSendFriendRequest, useAcceptFriendRequest } from "../hooks/useFriends";
import { useSearchPlayers } from "../hooks/usePlayer";
import type { PlayerDtoWithName } from "../models/friendship";

export function FriendsPage() {
    const { isAuthenticated, loggedInUser, login } = useContext(SecurityContext);
    const playerId = loggedInUser?.id ?? null;

    const [searchQuery, setSearchQuery] = useState("");
    const [submittedQuery, setSubmittedQuery] = useState("");

    const { data: friends, isLoading: loadingFriends } = useFriends(playerId);
    const { data: pendingRequests, isLoading: loadingPending } = usePendingRequests(playerId);

    const searchMutation = useSearchPlayers(submittedQuery);
    const sendFriendRequestMutation = useSendFriendRequest();
    const acceptFriendRequestMutation = useAcceptFriendRequest();

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();
        setSubmittedQuery(searchQuery.trim());
    };

    const handleSendFriendRequest = (toPlayerId: string) => {
        if (!playerId) return;
        sendFriendRequestMutation.mutate({ fromPlayerId: playerId, toPlayerId });
    };

    const handleAcceptFriendRequest = (fromPlayerId: string) => {
        if (!playerId) return;
        acceptFriendRequestMutation.mutate({ fromPlayerId, toPlayerId: playerId });
    };

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
                <PersonRoundedIcon sx={{ fontSize: 48, color: "#9d7dff", mb: 2 }} />
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
                background: "linear-gradient(135deg, #182736 0%, #351c2c 100%)",
                py: 4,
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
                    <Typography variant="h4" sx={{ fontWeight: 700, color: "#fff" }}>
                        Your Friends
                    </Typography>

                    {/* Friends List */}
                    {loadingFriends ? (
                        <Stack alignItems="center" py={5}>
                            <CircularProgress sx={{ color: "#fff" }} />
                            <Typography sx={{ color: "#d0d0e5", mt: 2 }}>Loading friends...</Typography>
                        </Stack>
                    ) : (
                        <Grid container spacing={2}>
                            {friends?.map((friend: PlayerDtoWithName) => (
                                <Grid size={{xs: 12, sm:6 , md: 4}} key={friend.playerId}>
                                    <Stack
                                        direction="row"
                                        alignItems="center"
                                        justifyContent="space-between"
                                        sx={{ p: 2, bgcolor: "rgba(255,255,255,0.05)", borderRadius: 2 }}
                                    >
                                        <Typography sx={{ color: "#fff" }}>{friend.username}</Typography>
                                    </Stack>
                                </Grid>
                            ))}
                        </Grid>
                    )}

                    {/* Pending Friend Requests */}
                    {loadingPending ? (
                        <Stack alignItems="center" py={2}>
                            <CircularProgress sx={{ color: "#fff" }} />
                        </Stack>
                    ) : pendingRequests?.length ? (
                        <>
                            <Typography variant="h5" sx={{ fontWeight: 600, color: "#fff" }}>
                                Pending Requests
                            </Typography>
                            <Grid container spacing={2}>
                                {pendingRequests.map((request: PlayerDtoWithName) => (
                                    <Grid size={{xs: 12, sm:6 , md: 4}} key={request.playerId}>
                                        <Stack
                                            direction="row"
                                            alignItems="center"
                                            justifyContent="space-between"
                                            sx={{ p: 2, bgcolor: "rgba(255,255,255,0.05)", borderRadius: 2 }}
                                        >
                                            <Typography sx={{ color: "#fff" }}>{request.username}</Typography>
                                            <Button
                                                variant="contained"
                                                size="small"
                                                onClick={() => handleAcceptFriendRequest(request.playerId)}
                                                sx={{ bgcolor: "#28a745", "&:hover": { bgcolor: "#218838" }, textTransform: "none" }}
                                            >
                                                Accept
                                            </Button>
                                        </Stack>
                                    </Grid>
                                ))}
                            </Grid>
                        </>
                    ) : null}

                    {/* Search New Players */}
                    <Box>
                        <Typography variant="h5" sx={{ fontWeight: 600, color: "#fff", mb: 2 }}>
                            Find Players
                        </Typography>
                        <form onSubmit={handleSearch}>
                            <Stack direction={{ xs: "column", sm: "row" }} spacing={2} mb={2}>
                                <TextField
                                    value={searchQuery}
                                    onChange={(e) => setSearchQuery(e.target.value)}
                                    placeholder="Enter username..."
                                    fullWidth
                                    sx={{ bgcolor: "#fff", borderRadius: 2 }}
                                />
                                <Button
                                    type="submit"
                                    variant="contained"
                                    sx={{ bgcolor: "#9d7dff", "&:hover": { bgcolor: "#7f5aff" }, color: "#fff", textTransform: "none", px: 4 }}
                                >
                                    Search
                                </Button>
                            </Stack>
                        </form>

                        {searchMutation.isLoading && (
                            <Stack alignItems="center" py={2}>
                                <CircularProgress sx={{ color: "#fff" }} />
                                <Typography sx={{ color: "#d0d0e5", mt: 1 }}>Searching players...</Typography>
                            </Stack>
                        )}

                        <Grid container spacing={2}>
                            {searchMutation.data?.map((player: PlayerDtoWithName) => (
                                <Grid size={{xs: 12, sm:6 , md: 4}} key={player.playerId}>
                                    <Stack
                                        direction="row"
                                        alignItems="center"
                                        justifyContent="space-between"
                                        sx={{ p: 2, bgcolor: "rgba(255,255,255,0.05)", borderRadius: 2 }}
                                    >
                                        <Typography sx={{ color: "#fff" }}>{player.username}</Typography>
                                        <Button
                                            variant="contained"
                                            size="small"
                                            startIcon={<PersonAddRoundedIcon />}
                                            onClick={() => handleSendFriendRequest(player.playerId)}
                                            disabled={sendFriendRequestMutation.isPending}
                                            sx={{ bgcolor: "#28a745", "&:hover": { bgcolor: "#218838" }, textTransform: "none" }}
                                        >
                                            {sendFriendRequestMutation.isPending ? "Sending..." : "Add Friend"}
                                        </Button>
                                    </Stack>
                                </Grid>
                            ))}
                        </Grid>
                    </Box>
                </Stack>
            </Box>
        </Box>
    );
}
