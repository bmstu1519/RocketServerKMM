package org.rocketserverkmm.project.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.SettingsRepository
import org.rocketserverkmm.project.presentation.states.AuthResult
import org.rocketserverkmm.project.presentation.viewmodels.Constants.THEME_KEY

class SettingsRepositoryImpl(
    private val kVault: KeyVaultRepository
) : SettingsRepository {
    private val isDarkThemeFlow = MutableStateFlow(kVault.getBoolean(THEME_KEY) ?: false)

    override suspend fun changeTheme(isDarkTheme: Boolean) : Boolean {
        val isDark = !isDarkTheme
        isDarkThemeFlow.value = isDarkTheme
        kVault.saveBoolean(THEME_KEY, isDark)
        return isDark
    }

    override suspend fun clickAuth(key: String): AuthResult = runCatching {
            val isAuthorized = kVault.getToken(key)
            if (isAuthorized.isNullOrEmpty()) {
                AuthResult.RequiresLogin
            } else {
                AuthResult.RequiresLogout
            }
    }.getOrElse { exception ->
        AuthResult.Error(exception.message ?: "Unknown error")
    }

    override suspend fun logOut(key: String) {
        runCatching {
            kVault.deleteToken(key)
        }
    }
}