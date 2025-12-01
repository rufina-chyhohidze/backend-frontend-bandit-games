import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import SecurityContextProvider from "./context/SecurityContextProvider";
import PublicPage from "./pages/PublicPage.tsx";
import { GamesPage } from "./pages/GamePage.tsx";
import { LobbyPage } from "./pages/LobbyPage.tsx";
import { TopNavBar } from "./components/layout/TopNavBar.tsx";
import { Box } from "@mui/material";

const queryClient = new QueryClient();

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <SecurityContextProvider>
                <BrowserRouter>
                    <Box
                        sx={{
                            display: "flex",
                            flexDirection: "column",
                            height: "100vh",
                            width: "100vw",
                            overflow: "hidden",
                        }}
                    >
                        <TopNavBar />
                        <Box
                            component="main"
                            sx={{
                                flex: 1,
                                overflow: "auto",
                                width: "100%",
                            }}
                        >
                            <Routes>
                                <Route path="/" element={<PublicPage />} />
                                <Route path="/games" element={<GamesPage />} />
                                <Route path="/lobby" element={<LobbyPage />} />
                            </Routes>
                        </Box>
                    </Box>
                </BrowserRouter>
            </SecurityContextProvider>
        </QueryClientProvider>
    );
}

export default App;