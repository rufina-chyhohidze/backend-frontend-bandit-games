import SecurityContext from "../../context/SecurityContext.ts";
import {useContext} from "react";
import {Navigate} from "react-router-dom";


export function DefaultRedirect() {
    const { loggedInUser } = useContext(SecurityContext);

    if (!loggedInUser) return <Navigate to="/public" replace />;

    return loggedInUser.roles.includes("admin")
        ? <Navigate to="/admin" replace />
        : <Navigate to="/games" replace />;
}
