import axios from "axios";
import type { AdminGame } from "../models/adminGame";

const BACKEND_URL: string = import.meta.env.VITE_BACKEND_URL;
const API_URL = `${BACKEND_URL}/api/admin/games`;

export const fetchPendingGames = async (): Promise<AdminGame[]> => {
    const response = await axios.get(`${API_URL}/pending`);
    return response.data;
};

export const approveGame = async (gameId: string): Promise<AdminGame> => {
    const response = await axios.post(`${API_URL}/${gameId}/approve`);
    return response.data;
};

export const rejectGame = async (gameId: string): Promise<AdminGame> => {
    const response = await axios.post(`${API_URL}/${gameId}/reject`);
    return response.data;
};
