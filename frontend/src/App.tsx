import "./App.css";
import { AppRoutes } from "./router/AppRoutes";
import { BrowserRouter } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import SecurityContextProvider from "./context/SecurityContextProvider";
import { ChatProvider } from "./context/ChatProvider.tsx";
import ChatbotWidget from "./components/Chatbot/ChatbotWidget";

const queryClient = new QueryClient();

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <SecurityContextProvider>
                <ChatProvider>
                    <BrowserRouter>
                        <AppRoutes />
                        <ChatbotWidget />
                    </BrowserRouter>
                </ChatProvider>
            </SecurityContextProvider>
        </QueryClientProvider>
    );
}

export default App;