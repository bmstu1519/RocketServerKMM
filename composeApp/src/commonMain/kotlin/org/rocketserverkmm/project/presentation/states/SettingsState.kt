package org.rocketserverkmm.project.presentation.states

data class SettingsState(
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false,
    val authButtonText: String? = null,
    val error: String? = null
)

sealed class SettingsAction {
    data object ClickAuthButton : SettingsAction()
    data object ChangeTheme : SettingsAction()
}

sealed class SettingsDestination {
    data object GoToLogin : SettingsDestination()
    data object ShowAlert : SettingsDestination()
}

sealed class AuthResult {
    data object RequiresLogin : AuthResult()
    data object RequiresLogout : AuthResult()
    data class Error(val message: String) : AuthResult()
}