import { Box, Chip, Grid, Stack, Typography } from "@mui/material";
import GroupRoundedIcon from "@mui/icons-material/GroupRounded";
import MailRoundedIcon from "@mui/icons-material/MailRounded";
import RocketLaunchRoundedIcon from "@mui/icons-material/RocketLaunchRounded";

import type { PlayerDtoWithName } from "../../models/friendship";
import { FriendsPanel } from "./FriendsPanel";
import { GameInvitesPanel } from "./GameInvitesPanel";
import { FriendRequestsPanel } from "./FriendRequestsPanel";
import { SentRequestsPanel } from "./SentRequestsPanel";
import { SearchPlayersPanel } from "./SearchPlayersPanel";
import React from "react";

type Props = {
    friends: PlayerDtoWithName[];
    loadingFriends: boolean;

    pendingRequests: PlayerDtoWithName[];
    loadingPending: boolean;

    sentRequests: PlayerDtoWithName[];

    pendingGameInvites: any[];
    loadingGameInvites: boolean;

    guessFromName: (fromId: string) => string;
    normalizeInvitationId: (inv: any) => string;
    normalizeFromPlayerId: (inv: any) => string;
    normalizeLobbyId: (inv: any) => string;

    searchQuery: string;
    setSearchQuery: (v: string) => void;
    submittedQuery: string;
    onSearchSubmit: (e: React.FormEvent) => void;
    searchLoading: boolean;
    searchResults: PlayerDtoWithName[];

    onInviteToLobby: (friendId: string) => void;
    onRemoveFriend: (friendId: string) => void;

    onAcceptFriendRequest: (fromPlayerId: string) => void;
    onRejectFriendRequest: (fromPlayerId: string) => void;

    onSendFriendRequest: (toPlayerId: string) => void;

    onAcceptGameInvite: (invitationId: string) => void;
    onRejectGameInvite: (invitationId: string) => void;

    invitePending: boolean;
    removePending: boolean;
    acceptReqPending: boolean;
    rejectReqPending: boolean;
    sendReqPending: boolean;
    acceptInvitePending: boolean;
    rejectInvitePending: boolean;
};

export function FriendsLayout(props: Props) {
    const pageBg = "linear-gradient(135deg, #101a2a 0%, #2a1422 45%, #0e0f1a 100%)";

    const shellSx = {
        width: "100%",
        maxWidth: { xs: "96vw", md: 900 },
        borderRadius: 4,
        p: { xs: 1.75, md: 2.5 },
        bgcolor: "rgba(10, 14, 28, 0.84)",
        border: "1px solid rgba(255,255,255,0.08)",
        boxShadow: "0 22px 70px rgba(0,0,0,0.7)",
        backdropFilter: "blur(10px)",
    } as const;

    return (
        <Box
            sx={{
                minHeight: "100vh",
                background: pageBg,
                pt: { xs: 9, md: 10 },
                pb: 4,
                px: { xs: 1.25, md: 2 },
                display: "flex",
                justifyContent: "center",
            }}
        >
            <Box sx={shellSx}>
                <Stack
                    direction={{ xs: "column", sm: "row" }}
                    spacing={1.25}
                    alignItems={{ xs: "flex-start", sm: "center" }}
                    justifyContent="space-between"
                    sx={{ mb: 2.25 }}
                >
                    <Box>
                        <Typography
                            variant="h5"
                            sx={{ fontWeight: 900, color: "#fff" }}
                        >
                            Friends
                        </Typography>
                        <Typography
                            variant="caption"
                            sx={{ color: "rgba(255,255,255,0.72)" }}
                        >
                            Friends • Requests • Invites
                        </Typography>
                    </Box>

                    <Stack direction="row" spacing={1} alignItems="center">
                        <Chip
                            icon={<GroupRoundedIcon />}
                            label={props.friends.length}
                            size="small"
                            sx={{ bgcolor: "rgba(157,125,255,0.16)", color: "#cdbfff" }}
                        />
                        <Chip
                            icon={<MailRoundedIcon />}
                            label={props.pendingRequests.length}
                            size="small"
                            sx={{ bgcolor: "rgba(255,193,7,0.16)", color: "#ffd36a" }}
                        />
                        <Chip
                            icon={<RocketLaunchRoundedIcon />}
                            label={props.pendingGameInvites.length}
                            size="small"
                            sx={{ bgcolor: "rgba(0,123,255,0.16)", color: "#8bc2ff" }}
                        />
                    </Stack>
                </Stack>

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "center",
                        mb: 3,
                    }}
                >
                    <Box
                        sx={{
                            width: { xs: "100%", sm: "80%", md: "70%" },
                        }}
                    >
                        <SearchPlayersPanel
                            searchQuery={props.searchQuery}
                            setSearchQuery={props.setSearchQuery}
                            submittedQuery={props.submittedQuery}
                            onSubmit={props.onSearchSubmit}
                            loading={props.searchLoading}
                            results={props.searchResults}
                            onAdd={props.onSendFriendRequest}
                            addPending={props.sendReqPending}
                        />
                    </Box>
                </Box>

                <Grid
                    container
                    spacing={2.5}
                    alignItems="stretch"
                >
                    <Grid item xs={12} md={6}>
                        <FriendsPanel
                            friends={props.friends}
                            loading={props.loadingFriends}
                            onInviteToLobby={props.onInviteToLobby}
                            onRemoveFriend={props.onRemoveFriend}
                            invitePending={props.invitePending}
                            removePending={props.removePending}
                        />
                    </Grid>

                    <Grid item xs={12} md={6}>
                        <GameInvitesPanel
                            loading={props.loadingGameInvites}
                            invites={props.pendingGameInvites}
                            guessFromName={props.guessFromName}
                            normalizeInvitationId={props.normalizeInvitationId}
                            normalizeFromPlayerId={props.normalizeFromPlayerId}
                            normalizeLobbyId={props.normalizeLobbyId}
                            onAccept={props.onAcceptGameInvite}
                            onReject={props.onRejectGameInvite}
                            acceptPending={props.acceptInvitePending}
                            rejectPending={props.rejectInvitePending}
                        />
                    </Grid>

                    <Grid item xs={12} md={6}>
                        <FriendRequestsPanel
                            loading={props.loadingPending}
                            requests={props.pendingRequests}
                            onAccept={props.onAcceptFriendRequest}
                            onReject={props.onRejectFriendRequest}
                            acceptPending={props.acceptReqPending}
                            rejectPending={props.rejectReqPending}
                        />
                    </Grid>

                    <Grid item xs={12} md={6}>
                        <SentRequestsPanel sentRequests={props.sentRequests} />
                    </Grid>
                </Grid>
            </Box>
        </Box>
    );
}
