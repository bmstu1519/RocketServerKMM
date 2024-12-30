package org.rocketserverkmm.project.repositories

import org.rocketserverkmm.project.domain.repositories.AppBootstrapRepository
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.presentation.states.UserAuthState

class AppBootstrapRepositoryImpl(
    private val kVault: KeyVaultRepository
): AppBootstrapRepository {
    override suspend fun getUserAuth(key: String): UserAuthState = runCatching {
        val isUserAuthorized = kVault.getBoolean(key) ?: false
        if (isUserAuthorized) {
            UserAuthState.AUTHORIZED
        } else {
            UserAuthState.NON_AUTHORIZED
        }
    }.getOrElse {
        UserAuthState.NON_AUTHORIZED
    }

    override suspend fun getCurrentTheme(key: String): Boolean = kVault.getBoolean(key) ?: false
}