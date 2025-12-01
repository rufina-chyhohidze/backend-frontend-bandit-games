import { type PropsWithChildren, useContext, useEffect } from 'react'
import { Navigate } from 'react-router-dom'
import SecurityContext from '../../context/SecurityContext.ts'

export function AdminRoute({ children }: PropsWithChildren) {
    const { isInitialised, isAuthenticated, loggedInUser, login } = useContext(SecurityContext)

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

    const roles = loggedInUser?.roles ?? []
    const isAdmin = roles.includes('admin')

    if (!isAdmin) {
        return <Navigate to="/public" replace />
    }

    return children
}
