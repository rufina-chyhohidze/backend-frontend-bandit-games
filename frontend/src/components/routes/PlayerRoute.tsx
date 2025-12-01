import { type PropsWithChildren, useContext } from "react";
import { Navigate } from "react-router-dom";
import SecurityContext from "../../context/SecurityContext";

export function PlayerRoute({ children }: PropsWithChildren) {
    const { loggedInUser } = useContext(SecurityContext);
    if (!loggedInUser) {
        return <Navigate to="/public" replace />;
    }

    const roles = loggedInUser.roles ?? [];
    const isAdmin = roles.includes("admin");
    const isPlayer = roles.includes("player");

    if (isAdmin) {
        return <Navigate to="/admin" replace />;
    }

    if (!isPlayer) {
        return <Navigate to="/public" replace />;
    }
    return <>{children}</>;
}