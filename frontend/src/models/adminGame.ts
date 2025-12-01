export type GameStatus = "DRAFT" | "PUBLISHED" | "REJECTED";

export interface AdminGame {
    gameId: string;
    name: string;
    description: string;
    rules: string;
    pictureUrl: string;
    urlGameSession: string;
    status: GameStatus;
}
