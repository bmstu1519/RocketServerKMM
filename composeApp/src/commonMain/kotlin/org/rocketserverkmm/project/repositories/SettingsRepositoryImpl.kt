package org.rocketserverkmm.project.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.SettingsRepository

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

    companion object {
        private const val THEME_KEY = "THEME_IS_DARK"
    }
}