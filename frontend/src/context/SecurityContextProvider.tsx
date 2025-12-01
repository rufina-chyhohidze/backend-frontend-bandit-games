import { type PropsWithChildren, useEffect, useState } from 'react'
import Keycloak, { type KeycloakConfig } from 'keycloak-js'
import { isExpired } from 'react-jwt'
import SecurityContext from './SecurityContext'
import type { User } from '../models/user'
import { addAccessTokenToAuthHeader, removeAccessTokenFromAuthHeader } from '../services/auth'

const keycloakConfig: KeycloakConfig = {
    url: import.meta.env.VITE_KC_URL,
    realm: import.meta.env.VITE_KC_REALM,
    clientId: import.meta.env.VITE_KC_CLIENT_ID,
}

const keycloak = new Keycloak(keycloakConfig)

export default function SecurityContextProvider({ children }: PropsWithChildren) {
    const [loggedInUser, setLoggedInUser] = useState<User | undefined>(undefined)
    const [isInitialised, setIsInitialised] = useState(false)

    useEffect(() => {
        keycloak.onReady = () => {
            setIsInitialised(true)
            if (keycloak.authenticated && keycloak.token) {
                addAccessTokenToAuthHeader(keycloak.token)
                updateUserFromToken()
            }
        }

        keycloak.onAuthSuccess = () => {
            addAccessTokenToAuthHeader(keycloak.token)
            updateUserFromToken()
        }

        keycloak.onAuthLogout = () => {
            removeAccessTokenFromAuthHeader()
            setLoggedInUser(undefined)
        }

        keycloak.onAuthError = () => {
            removeAccessTokenFromAuthHeader()
            setLoggedInUser(undefined)
        }

        keycloak.onTokenExpired = () => {
            keycloak.updateToken(-1).then(() => {
                addAccessTokenToAuthHeader(keycloak.token)
                updateUserFromToken()
            })
                // Fix 2: Add catch block for failed token refresh (best practice)
                .catch((err) => {
                    console.error('Token refresh failed', err)
                    keycloak.clearToken()
                    removeAccessTokenFromAuthHeader()
                    setLoggedInUser(undefined)
                })
        }

        keycloak
            .init({
                onLoad: 'check-sso',
                pkceMethod: 'S256',
                checkLoginIframe: false,
            })
            .catch((err) => {
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
        // Use keycloak.authenticated as the primary check
        if (keycloak.authenticated && keycloak.token) return !isExpired(keycloak.token)
        return false
    }

    function updateUserFromToken() {
        if (!keycloak.idTokenParsed || !keycloak.tokenParsed) return

        const idToken: any = keycloak.idTokenParsed
        const token: any = keycloak.tokenParsed

        // FIX: Extract the unique User ID (Subject) from the token.
        // The 'sub' claim is the correct user ID.
        const id = idToken.sub || token.sub

        const name =
            idToken.given_name ||
            idToken.preferred_username ||
            idToken.name ||
            'Unknown'

        const realmRoles: string[] = token.realm_access?.roles ?? []

        setLoggedInUser({
            id, // Use the corrected 'sub' claim for the ID
            name,
            roles: realmRoles,
        })
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