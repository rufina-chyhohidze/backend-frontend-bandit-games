import "./App.css";
import { AppRoutes } from "./router/AppRoutes";
import { BrowserRouter } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import SecurityContextProvider from "./context/SecurityContextProvider";

const queryClient = new QueryClient();

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <SecurityContextProvider>
                <BrowserRouter>
                    <AppRoutes />
                </BrowserRouter>
            </SecurityContextProvider>
        </QueryClientProvider>
    );
}

export default App;
