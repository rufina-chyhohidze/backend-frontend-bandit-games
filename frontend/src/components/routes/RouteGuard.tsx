import { type PropsWithChildren, useContext } from 'react'
import SecurityContext from '../../context/SecurityContext.ts'
import {Navigate} from "react-router-dom";

export function RouteGuard({ children }: PropsWithChildren) {
    const { isInitialised, isAuthenticated } = useContext(SecurityContext);

    if (!isInitialised) {
        return <div>Loading authentication...</div>;
    }

    if (!isAuthenticated()) {
        return <Navigate to="/public" replace />;
    }

    return <>{children}</>;
}
