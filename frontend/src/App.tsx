import "./App.css";
import { BrowserRouter } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import SecurityContextProvider from "./context/SecurityContextProvider";
import { AppRoutes } from "./router/AppRoutes";

const queryClient = new QueryClient();

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <SecurityContextProvider>
                <BrowserRouter>
                    <AppRoutes/>
                </BrowserRouter>
            </SecurityContextProvider>
        </QueryClientProvider>
    );
}

export default App;

