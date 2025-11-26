import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { GamesPage } from "../pages/GamePage";
import GameAchievementsPage from "../pages/GameAchievementsPage.tsx";
import {FullScreenFrame} from "../components/connect4/FullScreenFrame.tsx";

export function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/games" replace />} />
                <Route path="/games" element={<GamesPage />} />

                <Route path="/games/connect4" element={<FullScreenFrame src="/connect4/index.html" />} />

                <Route path="/games/:gameId/achievements" element={<GameAchievementsPage />} />
                <Route path="*" element={<p>Not found</p>} />
            </Routes>
        </BrowserRouter>
    );
}
