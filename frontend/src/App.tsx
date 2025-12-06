import "./App.css";
import { BrowserRouter } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import SecurityContextProvider from "./context/SecurityContextProvider";
import { AppRoutes } from "./router/AppRoutes";
import {ChatProvider} from "./context/ChatProvider.tsx";
import ChatbotWidget from "./components/Chatbot/ChatbotWidget";
import {TopNavBar} from "./components/layout/TopNavBar.tsx";

const queryClient = new QueryClient();

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <SecurityContextProvider>
                <ChatProvider>
                    <BrowserRouter>
                        <TopNavBar/>
                        <AppRoutes/>
                        <ChatbotWidget />
                    </BrowserRouter>
                </ChatProvider>
            </SecurityContextProvider>
        </QueryClientProvider>
    );
}

export default App;