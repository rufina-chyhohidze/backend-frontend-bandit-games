import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { GamesPage } from "../pages/GamePage";
import { ConnectFourPage } from "../pages/ConnectFourPage";

export function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/games" replace />} />
                <Route path="/games" element={<GamesPage />} />
                <Route path="/games/connect4" element={<ConnectFourPage />} />
                <Route path="*" element={<p>Not found</p>} />
            </Routes>
        </BrowserRouter>
    );
}
