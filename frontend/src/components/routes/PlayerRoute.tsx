import { type PropsWithChildren, useContext } from "react";
import { Navigate } from "react-router-dom";
import SecurityContext from "../../context/SecurityContext";

export function PlayerRoute({ children }: PropsWithChildren) {
    const { loggedInUser } = useContext(SecurityContext);

    return loggedInUser?.roles.includes("player")
        ? <>{children}</>
        : <Navigate to="/public" replace />;
}
