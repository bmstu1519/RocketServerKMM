package org.rocketserverkmm.project.domain.repositories

import org.rocketserverkmm.project.presentation.states.AuthResult

interface SettingsRepository {
    suspend fun changeTheme(isDarkTheme: Boolean) : Boolean
    suspend fun clickAuth(key: String) : AuthResult
    suspend fun logOut(key: String)
}