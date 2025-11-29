import { createContext } from 'react'
import type { User } from '../models/user.ts'

export type SecurityContextType = {
    isInitialised: boolean
    isAuthenticated: () => boolean
    loggedInUser: User | undefined
    login: () => void
    logout: () => void
}

const SecurityContext = createContext<SecurityContextType>({
    isInitialised: false,
    isAuthenticated: () => false,
    loggedInUser: undefined,
    login: () => {},
    logout: () => {},
})

export default SecurityContext
