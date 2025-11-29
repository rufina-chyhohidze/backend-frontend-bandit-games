import { useContext } from "react";
import { Navigate } from "react-router-dom";
import SecurityContext from "../context/SecurityContext";

export function RootRedirect() {
    const { isAuthenticated, loggedInUser } = useContext(SecurityContext);

    if (!isAuthenticated()) {
        return <Navigate to="/public" replace />;
    }

    const roles = loggedInUser?.roles ?? [];
    if (roles.includes("admin")) {
        return <Navigate to="/admin" replace />;
    }
    if (roles.includes("player")) {
        return <Navigate to="/games" replace />;
    }

    return <Navigate to="/public" replace />;
}
