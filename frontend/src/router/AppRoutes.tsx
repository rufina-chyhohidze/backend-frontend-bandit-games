import { Routes, Route, Outlet } from "react-router-dom";
import { GamesPage } from "../pages/GamePage";
import GameAchievementsPage from "../pages/GameAchievementsPage";
import { AdminPage } from "../pages/AdminPage";
import { PublicPage } from "../pages/PublicPage";
import { RouteGuard } from "../components/routes/RouteGuard";
import { FullScreenFrame } from "../components/connect4/FullScreenFrame";
import { LobbyPage } from "../pages/LobbyPage";
import { DefaultRedirect } from "../components/routes/DefaultRedirect.tsx";
import { FriendsPage } from "../pages/FriendsPage.tsx";
import { FavoriteGamesPage } from "../pages/FavoriteGamesPage";

export function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<DefaultRedirect />} />

            <Route path="/public" element={<PublicPage />} />

            <Route element={<RouteGuard><Outlet /></RouteGuard>}>

                <Route
                    path="/admin"
                    element={
                        <RouteGuard role="admin">
                            <AdminPage />
                        </RouteGuard>
                    }
                />

                <Route
                    path="/games"
                    element={
                        <RouteGuard role="player">
                            <GamesPage />
                        </RouteGuard>
                    }
                />

                <Route
                    path="/favorites"
                    element={
                        <RouteGuard role="player">
                            <FavoriteGamesPage />
                        </RouteGuard>
                    }
                />

                <Route
                    path="/games/connect4"
                    element={
                        <RouteGuard role="player">
                            <FullScreenFrame src="/connect4/index.html" />
                        </RouteGuard>
                    }
                />

                <Route
                    path="/games/:gameId/achievements"
                    element={
                        <RouteGuard role="player">
                            <GameAchievementsPage />
                        </RouteGuard>
                    }
                />

                <Route
                    path="/lobby"
                    element={
                        <RouteGuard role="player">
                            <LobbyPage />
                        </RouteGuard>
                    }
                />

                <Route
                    path="/friends"
                    element={
                        <RouteGuard role="player">
                            <FriendsPage />
                        </RouteGuard>
                    }
                />
            </Route>


            <Route path="*" element={<p>Not found</p>} />
        </Routes>
    );
}
