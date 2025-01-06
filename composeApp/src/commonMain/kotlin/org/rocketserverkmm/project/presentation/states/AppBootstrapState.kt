package org.rocketserverkmm.project.presentation.states

data class AppBootstrapState(
    val isUserAuthorized: UserAuthState,
    val isDarkThemeEnabled: Boolean = false
)

enum class UserAuthState {
    NON_AUTHORIZED,
    AUTHORIZED,
    NO_IMPLEMENTATION
}

sealed class AppBootstrapAction {
    data object Load : AppBootstrapAction()
}