import { type PropsWithChildren, useEffect, useState, useRef } from 'react'
import Keycloak, { type KeycloakConfig } from 'keycloak-js'
import { isExpired } from 'react-jwt'
import SecurityContext from './SecurityContext'
import type { User } from '../models/user'
import { addAccessTokenToAuthHeader, removeAccessTokenFromAuthHeader } from '../services/auth'
import { registerPlayer } from '../services/playerService'

const keycloakConfig: KeycloakConfig = {
    url: import.meta.env.VITE_KC_URL,
    realm: import.meta.env.VITE_KC_REALM,
    clientId: import.meta.env.VITE_KC_CLIENT_ID,
}

const keycloak = new Keycloak(keycloakConfig)

export default function SecurityContextProvider({ children }: PropsWithChildren) {
    const [loggedInUser, setLoggedInUser] = useState<User | undefined>(undefined)
    const [isInitialised, setIsInitialised] = useState(false)
    const hasRegisteredPlayer = useRef(false) // <- ref avoids re-renders

    useEffect(() => {
        const handleUserUpdate = () => {
            if (keycloak.authenticated && keycloak.token) {
                addAccessTokenToAuthHeader(keycloak.token)
                updateUserFromToken()
            }
        }

        keycloak.onReady = () => {
            setIsInitialised(true)
            handleUserUpdate()
        }

        // Removed onAuthSuccess to avoid double call
        // keycloak.onAuthSuccess = handleUserUpdate

        keycloak.onAuthLogout = () => {
            removeAccessTokenFromAuthHeader()
            setLoggedInUser(undefined)
        }

        keycloak.onAuthError = () => {
            removeAccessTokenFromAuthHeader()
            setLoggedInUser(undefined)
        }

        keycloak.onTokenExpired = () => {
            keycloak.updateToken(-1)
                .then(() => {
                    addAccessTokenToAuthHeader(keycloak.token)
                    updateUserFromToken()
                })
                .catch((err) => {
                    console.error('Token refresh failed', err)
                    keycloak.clearToken()
                    removeAccessTokenFromAuthHeader()
                    setLoggedInUser(undefined)
                })
        }

        keycloak.init({
            onLoad: 'check-sso',
            pkceMethod: 'S256',
            checkLoginIframe: false,
        }).catch((err) => {
            console.error('Keycloak init error', err)
            setIsInitialised(true)
        })
    }, [])

    function login() {
        keycloak.login()
    }

    function logout() {
        keycloak.logout()
    }

    function isAuthenticated() {
        return keycloak.authenticated && keycloak.token ? !isExpired(keycloak.token) : false
    }

    async function updateUserFromToken() {
        if (!keycloak.idTokenParsed || !keycloak.tokenParsed) return

        const idToken: any = keycloak.idTokenParsed
        const token: any = keycloak.tokenParsed

        const id = idToken.sub || token.sub
        const name = idToken.given_name || idToken.preferred_username || idToken.name || 'Unknown'

        const realmRoles: string[] = token.realm_access?.roles ?? []
        const clientId = import.meta.env.VITE_KC_CLIENT_ID
        const clientRoles: string[] = token.resource_access?.[clientId]?.roles ?? []
        const allRoles = [...realmRoles, ...clientRoles]

        setLoggedInUser({ id, name, roles: allRoles })

        // Only register once
        if (!hasRegisteredPlayer.current) {
            hasRegisteredPlayer.current = true // set before awaiting
            try {
                const backendPlayer = await registerPlayer()
                console.log('Backend player registered / fetched:', backendPlayer)
            } catch (err) {
                console.error('Failed to register backend player', err)
            }
        }
    }

    return (
        <SecurityContext.Provider
            value={{
                isInitialised,
                isAuthenticated,
                loggedInUser,
                login,
                logout,
            }}
        >
            {children}
        </SecurityContext.Provider>
    )
}
