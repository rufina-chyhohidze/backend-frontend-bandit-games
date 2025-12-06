import { Routes, Route, Outlet } from "react-router-dom";
import { GamesPage } from "../pages/GamePage";
import GameAchievementsPage from "../pages/GameAchievementsPage";
import { AdminPage } from "../pages/AdminPage";
import { PublicPage } from "../pages/PublicPage";
import { AdminRoute } from "../components/routes/AdminRoute";
import { PlayerRoute } from "../components/routes/PlayerRoute";
import { RouteGuard } from "../components/routes/RouteGuard";
import { FullScreenFrame } from "../components/connect4/FullScreenFrame";
import { LobbyPage } from "../pages/LobbyPage";
import {DefaultRedirect} from "../components/routes/DefaultRedirect.tsx";
import {FriendsPage} from "../pages/FriendsPage.tsx";

export function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<DefaultRedirect />} />

            <Route path="/public" element={<PublicPage />} />

            <Route element={<RouteGuard><Outlet /></RouteGuard>}>

                <Route path="/admin" element={
                    <AdminRoute>
                        <AdminPage />
                    </AdminRoute>
                } />

                <Route path="/games" element={
                    <PlayerRoute>
                        <GamesPage />
                    </PlayerRoute>
                } />

                <Route path="/games/connect4" element={
                    <PlayerRoute>
                        <FullScreenFrame src="/connect4/index.html" />
                    </PlayerRoute>
                } />

                <Route path="/games/:gameId/achievements" element={
                    <PlayerRoute>
                        <GameAchievementsPage />
                    </PlayerRoute>
                } />

                <Route path="/lobby" element={
                    <PlayerRoute>
                        <LobbyPage />
                    </PlayerRoute>
                } />

                <Route path="/friends" element={
                    <PlayerRoute>
                        <FriendsPage />
                    </PlayerRoute>
                } />
            </Route>

            <Route path="*" element={<p>Not found</p>} />
        </Routes>
    );
}
