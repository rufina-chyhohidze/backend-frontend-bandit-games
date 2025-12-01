import { Routes, Route } from "react-router-dom";
import { GamesPage } from "../pages/GamePage";
import GameAchievementsPage from "../pages/GameAchievementsPage";
import { AdminPage } from "../pages/AdminPage";
import { PublicPage } from "../pages/PublicPage";
import { AdminRoute } from "../components/routes/AdminRoute";
import { PlayerRoute } from "../components/routes/PlayerRoute";
import { RootRedirect } from "../components/RootRedirect";
import {FullScreenFrame} from "../components/connect4/FullScreenFrame.tsx";
import {LobbyPage} from "../pages/LobbyPage.tsx";

export function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<RootRedirect />} />

            <Route path="/public" element={<PublicPage />} />

            <Route
                path="/admin"
                element={
                    <AdminRoute>
                        <AdminPage />
                    </AdminRoute>
                }
            />

            <Route
                path="/games"
                element={
                    <PlayerRoute>
                        <GamesPage />
                    </PlayerRoute>
                }
            />
            <Route
                path="/games/connect4"
                element={
                    <PlayerRoute>
                        <FullScreenFrame src="/connect4/index.html" />
                    </PlayerRoute>
                }
            />
            <Route
                path="/games/:gameId/achievements"
                element={
                    <PlayerRoute>
                        <GameAchievementsPage />
                    </PlayerRoute>
                }
            />
            <Route path="/lobby" element={<LobbyPage />} />

            <Route path="*" element={<p>Not found</p>} />
        </Routes>
    );
}
