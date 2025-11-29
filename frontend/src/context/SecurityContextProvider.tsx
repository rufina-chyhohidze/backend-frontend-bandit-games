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
        if (keycloak.token) return !isExpired(keycloak.token)
        return false
    }

    function updateUserFromToken() {
        if (!keycloak.idTokenParsed || !keycloak.tokenParsed) return

        const idToken: any = keycloak.idTokenParsed
        const token: any = keycloak.tokenParsed

        const name =
            idToken.given_name ||
            idToken.preferred_username ||
            idToken.name ||
            'Unknown'

        const realmRoles: string[] = token.realm_access?.roles ?? []

        setLoggedInUser({
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
