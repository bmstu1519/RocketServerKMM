package org.rocketserverkmm.project.domain.repositories

interface SettingsRepository {
    suspend fun changeTheme(isDarkTheme: Boolean)
}