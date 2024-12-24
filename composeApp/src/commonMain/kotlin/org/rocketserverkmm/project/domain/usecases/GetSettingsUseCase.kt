package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.domain.models.login.LoginResult
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.LaunchRepository
import org.rocketserverkmm.project.domain.repositories.SettingsRepository
import org.rocketserverkmm.project.platform.KEY_TOKEN

class GetSettingsUseCase(
    private val repository: LaunchRepository,
    private val keyVaultRepository: KeyVaultRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend fun logIn(email: String) : LoginResult = repository.login(email)
    fun logOut(key: String = KEY_TOKEN) = keyVaultRepository.deleteToken(key)
    suspend fun changeTheme(isDarkTheme: Boolean) = settingsRepository.changeTheme(isDarkTheme)
}