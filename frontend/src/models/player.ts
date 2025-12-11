// models/player.ts
export interface PlayerDto {
    id: string;       // UUID
    username: string;
    favoriteGameIds: string[];
    achievements: string[];
}
