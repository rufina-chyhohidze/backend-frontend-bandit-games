import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
    acceptGameInvite,
    getPendingGameInvites,
    inviteToLobby,
    rejectGameInvite,
} from "../services/gameInvitationService";
import { useContext } from "react";
import SecurityContext from "../context/SecurityContext";
import type { GameInvitationDto } from "../models/gameInvitation";

export function usePendingGameInvites() {
    const { isAuthenticated } = useContext(SecurityContext);

    return useQuery<GameInvitationDto[]>({
        queryKey: ["game-invites", "pending"],
        queryFn: getPendingGameInvites,
        enabled: isAuthenticated(),
        refetchInterval: 5000,
        retry: false,
    });
}

export function useInviteToLobby() {
    const qc = useQueryClient();

    return useMutation<string, unknown, { toPlayerId: string }>({
        mutationFn: ({ toPlayerId }) => inviteToLobby(toPlayerId),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: ["game-invites", "pending"] });
            qc.invalidateQueries({ queryKey: ["lobby-by-player"] });
            qc.invalidateQueries({ queryKey: ["open-lobbies"] });
        },
    });
}

export function useAcceptGameInvite() {
    const qc = useQueryClient();

    return useMutation<{ lobbyId: string }, unknown, { invitationId: string }>({
        mutationFn: ({ invitationId }) => acceptGameInvite(invitationId),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: ["game-invites", "pending"] });
            qc.invalidateQueries({ queryKey: ["lobby-by-player"] });
            qc.invalidateQueries({ queryKey: ["open-lobbies"] });
        },
    });
}

export function useRejectGameInvite() {
    const qc = useQueryClient();

    return useMutation<void, unknown, { invitationId: string }>({
        mutationFn: ({ invitationId }) => rejectGameInvite(invitationId),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: ["game-invites", "pending"] });
        },
    });
}
