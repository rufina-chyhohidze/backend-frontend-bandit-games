import { type PropsWithChildren, useContext, useEffect } from 'react'
import SecurityContext from '../../context/SecurityContext.ts'

export function RouteGuard({ children }: PropsWithChildren) {
    const { isInitialised, isAuthenticated, login } = useContext(SecurityContext)

    useEffect(() => {
        if (isInitialised && !isAuthenticated()) {
            login()
        }
    }, [isInitialised, isAuthenticated, login])

    if (!isInitialised) {
        return <div>Initialising authentication...</div>
    }

    if (!isAuthenticated()) {
        return <div>Authenticating...</div>
    }

    return children
}
