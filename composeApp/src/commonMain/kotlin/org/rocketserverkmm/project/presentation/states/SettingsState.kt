package org.rocketserverkmm.project.presentation.states

data class SettingsState(
    val isLoading: Boolean = false,
    val isDarkTheme: Boolean = false,
    val authButtonText: String? = null,
    val actionableAlert: ActionableAlert? = null,
    val error: String? = null
)

sealed class SettingsAction {
    data object ClickAuthButton : SettingsAction()
    data object ShowAlert : SettingsAction()
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

data class ActionableAlert(
    val text: String,
    val submitButton: ActionableButton,
    val cancelButton: ActionableButton,
)

data class ActionableButton(
    val buttonText: String? = null,
    val action: () -> Unit
)