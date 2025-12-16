import axios from "axios";
import type { GameInvitationDto } from "../models/gameInvitation";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL;
const INV_API = `${BACKEND_URL}/api/game-invitations`;

export const inviteToLobby = async (toPlayerId: string): Promise<string> => {
    const res = await axios.post(`${INV_API}/invite`, { toPlayerId });
    return res.data;
};

export const getPendingGameInvites = async (): Promise<GameInvitationDto[]> => {
    const res = await axios.get<GameInvitationDto[]>(`${INV_API}/pending`);
    return res.data ?? [];
};

export const acceptGameInvite = async (invitationId: string): Promise<{ lobbyId: string }> => {
    const res = await axios.post(`${INV_API}/accept`, { invitationId });
    return res.data; // { lobbyId: "..." }
};

export const rejectGameInvite = async (invitationId: string): Promise<void> => {
    await axios.post(`${INV_API}/reject`, { invitationId });
};
