import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
    sendFriendRequest,
    acceptFriendRequest,
    rejectFriendRequest,
    removeFriend,
    getFriends,
    getPendingRequests,
    getFriendshipBetween, getSentRequests,
} from "../services/friendsService.ts";
import type { FriendshipDto, PlayerDtoWithName } from "../models/friendship";

// -----------------------------
// Send Friend Request
// -----------------------------
export function useSendFriendRequest() {
    const queryClient = useQueryClient();

    return useMutation<FriendshipDto, unknown, { fromPlayerId: string; toPlayerId: string }>({
        mutationFn: ({ fromPlayerId, toPlayerId }) => sendFriendRequest(fromPlayerId, toPlayerId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["friends"] });
            queryClient.invalidateQueries({ queryKey: ["pending-requests"] });
        },
    });
}

// -----------------------------
// Accept Friend Request
// -----------------------------
// hooks/useFriends.ts

// -----------------------------
// Accept Friend Request
// -----------------------------
export function useAcceptFriendRequest() {
    const queryClient = useQueryClient();

    return useMutation<void, unknown, { fromPlayerId: string; toPlayerId: string }>({
        mutationFn: ({ fromPlayerId, toPlayerId }) => acceptFriendRequest(fromPlayerId, toPlayerId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["friends"] });
            queryClient.invalidateQueries({ queryKey: ["pending-requests"] });
            queryClient.invalidateQueries({ queryKey: ["sent-requests"] });
        },
    });
}

// -----------------------------
// Reject Friend Request
// -----------------------------
export function useRejectFriendRequest() {
    const queryClient = useQueryClient();

    return useMutation<void, unknown, { fromPlayerId: string; toPlayerId: string }>({
        mutationFn: ({ fromPlayerId, toPlayerId }) => rejectFriendRequest(fromPlayerId, toPlayerId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["pending-requests"] });
            queryClient.invalidateQueries({ queryKey: ["sent-requests"] });
        },
    });
}
// -----------------------------
// Remove Friend
// -----------------------------
export function useRemoveFriend() {
    const queryClient = useQueryClient();

    return useMutation<void, unknown, { playerAId: string; playerBId: string }>({
        mutationFn: ({ playerAId, playerBId }) => removeFriend(playerAId, playerBId),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["friends"] });
            queryClient.invalidateQueries({ queryKey: ["pending-requests"] });
        },
    });
}

// -----------------------------
// Get Friends for a Player
// -----------------------------
export function useFriends(playerId: string | null) {
    return useQuery<PlayerDtoWithName[]>({
        queryKey: ["friends", playerId],
        queryFn: () => getFriends(playerId!),
        enabled: !!playerId,
        refetchInterval: 5000,
    });
}

// -----------------------------
// Get Pending Friend Requests
// -----------------------------
export function usePendingRequests(playerId: string | null) {
    return useQuery<PlayerDtoWithName[]>({
        queryKey: ["pending-requests", playerId],
        queryFn: () => getPendingRequests(playerId!),
        enabled: !!playerId,
        refetchInterval: 5000,
    });
}

// -----------------------------
// Get Friendship Between Two Players
// -----------------------------
export function useFriendshipBetween(playerAId: string, playerBId: string) {
    return useQuery<FriendshipDto | null>({
        queryKey: ["friendship-between", playerAId, playerBId],
        queryFn: async () => {
            const data = await getFriendshipBetween(playerAId, playerBId);
            return data ?? null;
        },
        enabled: !!playerAId && !!playerBId,
    });
}

// Add to hooks/useFriends.ts

// -----------------------------
// Get Sent Friend Requests (requests YOU sent to others)
// -----------------------------
export function useSentRequests(playerId: string | null) {
    return useQuery<PlayerDtoWithName[]>({
        queryKey: ["sent-requests", playerId],
        queryFn: () => getSentRequests(playerId!),
        enabled: !!playerId,
        refetchInterval: 5000,
    });
}
