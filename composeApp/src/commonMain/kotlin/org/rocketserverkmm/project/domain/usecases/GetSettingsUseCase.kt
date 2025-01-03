package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.domain.repositories.SettingsRepository
import org.rocketserverkmm.project.platform.KEY_TOKEN
import org.rocketserverkmm.project.presentation.states.ActionableAlert

class GetSettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend fun clickAuth(key: String = KEY_TOKEN) = settingsRepository.clickAuth(key)
    suspend fun changeTheme(isDarkTheme: Boolean) = settingsRepository.changeTheme(isDarkTheme)
    suspend fun logOut(key: String = KEY_TOKEN) = settingsRepository.logOut(key)
}