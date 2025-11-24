import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { GamesPage } from "../pages/GamePage";
import { ConnectFourPage } from "../pages/ConnectFourPage";
import GameAchievementsPage from "../pages/GameAchievementsPage.tsx";

export function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/games" replace />} />
                <Route path="/games" element={<GamesPage />} />
                <Route path="/games/connect4" element={<ConnectFourPage />} />
                <Route path="/games/:gameId/achievements" element={<GameAchievementsPage />} />
                <Route path="*" element={<p>Not found</p>} />
            </Routes>
        </BrowserRouter>
    );
}
