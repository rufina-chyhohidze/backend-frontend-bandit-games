import { useContext } from "react";
import { Navigate } from "react-router-dom";
import SecurityContext from "../context/SecurityContext";

export function RootRedirect() {
    const { isAuthenticated, loggedInUser } = useContext(SecurityContext);

    console.log("RootRedirect - isAuthenticated:", isAuthenticated());
    console.log("RootRedirect - loggedInUser:", loggedInUser);
    console.log("RootRedirect - roles:", loggedInUser?.roles);

    if (!isAuthenticated()) {
        console.log("RootRedirect - Redirecting to /public (not authenticated)");
        return <Navigate to="/public" replace />;
    }

    const roles = loggedInUser?.roles ?? [];
    console.log("RootRedirect - User roles:", roles);

    if (roles.includes("admin")) {
        console.log("RootRedirect - Redirecting to /admin (is admin)");
        return <Navigate to="/admin" replace />;
    }
    if (roles.includes("player")) {
        console.log("RootRedirect - Redirecting to /games (is player)");
        return <Navigate to="/games" replace />;
    }

    console.log("RootRedirect - Redirecting to /public (no valid roles)");
    return <Navigate to="/public" replace />;
}