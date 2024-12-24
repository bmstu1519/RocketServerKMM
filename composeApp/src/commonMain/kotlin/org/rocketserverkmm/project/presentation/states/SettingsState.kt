package org.rocketserverkmm.project.presentation.states

data class SettingsState(
    val isDarkTheme: Boolean = false,
    val authButtonText: String? = null
)

sealed class SettingsAction {
    data object ClickAuthButton : SettingsAction()
    data object ChangeTheme : SettingsAction()
}

sealed class SettingsDestination {
    data object LogIn : SettingsDestination()
    data object LogOut : SettingsDestination()
}