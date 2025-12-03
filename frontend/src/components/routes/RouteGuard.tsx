import { type PropsWithChildren, useContext } from "react";
import { Navigate } from "react-router-dom";
import SecurityContext from "../../context/SecurityContext.ts";

type RouteGuardProps = PropsWithChildren<{
    role?: string;
}>;

export function RouteGuard({ children, role }: RouteGuardProps) {
    const { isInitialised, isAuthenticated, loggedInUser } = useContext(SecurityContext);

    if (!isInitialised) {
        return <div>Loading authentication...</div>;
    }

    if (!isAuthenticated()) {
        return <Navigate to="/public" replace />;
    }

    if (role && !loggedInUser?.roles.includes(role)) {
        return <Navigate to="/public" replace />;
    }

    if (role === "player" && loggedInUser?.roles.includes("admin")) {
        return <Navigate to="/public" replace />;
    }

    return <>{children}</>;
}