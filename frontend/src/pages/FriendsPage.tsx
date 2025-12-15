import { useMemo, useState, useContext } from "react";
import { Box, Alert, Snackbar } from "@mui/material";
import { useNavigate } from "react-router-dom";

import SecurityContext from "../context/SecurityContext";
import type { PlayerDtoWithName } from "../models/friendship";

import {
    useFriends,
    usePendingRequests,
    useSendFriendRequest,
    useAcceptFriendRequest,
    useRejectFriendRequest,
    useRemoveFriend,
    useSentRequests,
} from "../hooks/useFriends";

import { useSearchPlayers } from "../hooks/usePlayer";

import {
    usePendingGameInvites,
    useInviteToLobby,
    useAcceptGameInvite,
    useRejectGameInvite,
} from "../hooks/useGameInvitation.ts";

import { AccessDeniedCard } from "../components/friends/AccessDeniedCard";
import { FriendsLayout } from "../components/friends/FriendsLayout";

export function FriendsPage() {
    const navigate = useNavigate();
    const { isAuthenticated, loggedInUser, login } = useContext(SecurityContext);
    const playerId = loggedInUser?.id ?? null;

    const [searchQuery, setSearchQuery] = useState("");
    const [submittedQuery, setSubmittedQuery] = useState("");
    const [snackbar, setSnackbar] = useState<{
        open: boolean;
        message: string;
        severity: "success" | "error";
    }>({ open: false, message: "", severity: "success" });

    const { data: friends, isLoading: loadingFriends } = useFriends(playerId);
    const { data: pendingRequests, isLoading: loadingPending } = usePendingRequests(playerId);
    const { data: sentRequests } = useSentRequests(playerId);
    const searchMutation = useSearchPlayers(submittedQuery);

    const sendFriendRequestMutation = useSendFriendRequest();
    const acceptFriendRequestMutation = useAcceptFriendRequest();
    const rejectFriendRequestMutation = useRejectFriendRequest();
    const removeFriendMutation = useRemoveFriend();

    const { data: pendingGameInvites, isLoading: loadingGameInvites } = usePendingGameInvites();
    const inviteToLobbyMutation = useInviteToLobby();
    const acceptGameInviteMutation = useAcceptGameInvite();
    const rejectGameInviteMutation = useRejectGameInvite();

    const [localSentRequests, setLocalSentRequests] = useState<Set<string>>(new Set());

    const closeSnackbar = () => setSnackbar((p) => ({ ...p, open: false }));

    const onSearchSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const q = searchQuery.trim();
        if (q) setSubmittedQuery(q);
    };

    const onSendFriendRequest = async (toPlayerId: string) => {
        if (!playerId) return;
        try {
            setLocalSentRequests((prev) => new Set(prev).add(toPlayerId));
            await sendFriendRequestMutation.mutateAsync({ fromPlayerId: playerId, toPlayerId });
            setSnackbar({ open: true, message: "Friend request sent!", severity: "success" });
        } catch (error: any) {
            const msg = error?.response?.data?.message || "Failed to send friend request";
            setSnackbar({ open: true, message: msg, severity: "error" });
        } finally {
            setLocalSentRequests((prev) => {
                const next = new Set(prev);
                next.delete(toPlayerId);
                return next;
            });
        }
    };

    const onAcceptFriendRequest = async (fromPlayerId: string) => {
        if (!playerId) return;
        try {
            await acceptFriendRequestMutation.mutateAsync({ fromPlayerId, toPlayerId: playerId });
            setSnackbar({ open: true, message: "Friend request accepted!", severity: "success" });
        } catch (error: any) {
            const msg = error?.response?.data?.message || "Failed to accept friend request";
            setSnackbar({ open: true, message: msg, severity: "error" });
        }
    };

    const onRejectFriendRequest = async (fromPlayerId: string) => {
        if (!playerId) return;
        try {
            await rejectFriendRequestMutation.mutateAsync({ fromPlayerId, toPlayerId: playerId });
            setSnackbar({ open: true, message: "Friend request rejected", severity: "success" });
        } catch (error: any) {
            const msg = error?.response?.data?.message || "Failed to reject friend request";
            setSnackbar({ open: true, message: msg, severity: "error" });
        }
    };

    const onRemoveFriend = async (friendId: string) => {
        if (!playerId) return;
        const confirmed = window.confirm("Are you sure you want to remove this friend?");
        if (!confirmed) return;

        try {
            await removeFriendMutation.mutateAsync({ playerAId: playerId, playerBId: friendId });
            setSnackbar({ open: true, message: "Friend removed", severity: "success" });
        } catch (error: any) {
            const msg = error?.response?.data?.message || "Failed to remove friend";
            setSnackbar({ open: true, message: msg, severity: "error" });
        }
    };

    const onInviteToLobby = async (friendId: string) => {
        try {
            await inviteToLobbyMutation.mutateAsync({ toPlayerId: friendId });
            setSnackbar({ open: true, message: "Invite sent! Opening lobby...", severity: "success" });
            navigate("/lobby");
        } catch (err: any) {
            const msg =
                err?.response?.data?.message ??
                (typeof err?.response?.data === "string" ? err.response.data : null) ??
                err?.message ??
                "Invite failed";
            setSnackbar({ open: true, message: msg, severity: "error" });
        }
    };

    const onAcceptGameInvite = async (invitationId: string) => {
        try {
            await acceptGameInviteMutation.mutateAsync({ invitationId });
            setSnackbar({ open: true, message: "Invite accepted! Redirecting to lobby...", severity: "success" });
            navigate("/lobby");
        } catch (error: any) {
            const msg = error?.response?.data || error?.response?.data?.message || "Failed to accept invite";
            setSnackbar({ open: true, message: String(msg), severity: "error" });
        }
    };

    const onRejectGameInvite = async (invitationId: string) => {
        try {
            await rejectGameInviteMutation.mutateAsync({ invitationId });
            setSnackbar({ open: true, message: "Invite rejected.", severity: "success" });
        } catch (error: any) {
            const msg = error?.response?.data || error?.response?.data?.message || "Failed to reject invite";
            setSnackbar({ open: true, message: String(msg), severity: "error" });
        }
    };

    const getPlayerStatus = (id: string): "friend" | "pending_received" | "pending_sent" | "none" => {
        if (friends?.some((f) => f.playerId === id)) return "friend";
        if (pendingRequests?.some((r) => r.playerId === id)) return "pending_received";
        if (sentRequests?.some((s) => s.playerId === id) || localSentRequests.has(id)) return "pending_sent";
        return "none";
    };

    const filteredSearchResults = useMemo(() => {
        return (
            searchMutation.data?.filter((p: PlayerDtoWithName) => {
                if (p.playerId === playerId) return false;
                return getPlayerStatus(p.playerId) === "none";
            }) || []
        );
    }, [searchMutation.data, playerId, friends, pendingRequests, sentRequests, localSentRequests]);

    const normalizeInvitationId = (inv: any): string => (typeof inv?.id === "string" ? inv.id : inv?.id?.id);
    const normalizeFromPlayerId = (inv: any): string =>
        typeof inv?.fromPlayer === "string" ? inv.fromPlayer : inv?.fromPlayer?.playerId ?? inv?.fromPlayerId;
    const normalizeLobbyId = (inv: any): string =>
        typeof inv?.lobbyId === "string" ? inv.lobbyId : inv?.lobbyId?.lobbyID ?? inv?.lobbyId?.lobbyId;

    const guessFromName = (fromId: string) => friends?.find((f) => f.playerId === fromId)?.username ?? "A friend";

    if (!isAuthenticated()) return <AccessDeniedCard onLogin={login} />;

    return (
        <Box>
            <FriendsLayout
                friends={friends ?? []}
                loadingFriends={loadingFriends}
                pendingRequests={pendingRequests ?? []}
                loadingPending={loadingPending}
                sentRequests={sentRequests ?? []}
                pendingGameInvites={pendingGameInvites ?? []}
                loadingGameInvites={loadingGameInvites}
                guessFromName={guessFromName}
                normalizeInvitationId={normalizeInvitationId}
                normalizeFromPlayerId={normalizeFromPlayerId}
                normalizeLobbyId={normalizeLobbyId}
                searchQuery={searchQuery}
                setSearchQuery={setSearchQuery}
                submittedQuery={submittedQuery}
                onSearchSubmit={onSearchSubmit}
                searchLoading={searchMutation.isLoading}
                searchResults={filteredSearchResults}
                onInviteToLobby={onInviteToLobby}
                onRemoveFriend={onRemoveFriend}
                onAcceptFriendRequest={onAcceptFriendRequest}
                onRejectFriendRequest={onRejectFriendRequest}
                onSendFriendRequest={onSendFriendRequest}
                onAcceptGameInvite={onAcceptGameInvite}
                onRejectGameInvite={onRejectGameInvite}
                invitePending={inviteToLobbyMutation.isPending}
                removePending={removeFriendMutation.isPending}
                acceptReqPending={acceptFriendRequestMutation.isPending}
                rejectReqPending={rejectFriendRequestMutation.isPending}
                sendReqPending={sendFriendRequestMutation.isPending}
                acceptInvitePending={acceptGameInviteMutation.isPending}
                rejectInvitePending={rejectGameInviteMutation.isPending}
            />

            <Snackbar
                open={snackbar.open}
                autoHideDuration={4000}
                onClose={closeSnackbar}
                anchorOrigin={{ vertical: "bottom", horizontal: "center" }}
            >
                <Alert onClose={closeSnackbar} severity={snackbar.severity} sx={{ width: "100%" }}>
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </Box>
    );
}
