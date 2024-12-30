package org.rocketserverkmm.project.presentation.states

data class AppBootstrapState(
    val isUserAuthorized: UserAuthState? = null,
    val isDarkThemeEnabled: Boolean = false
)

enum class UserAuthState {
    NON_AUTHORIZED,
    AUTHORIZED
}

sealed class AppBootstrapAction {
    data object Load : AppBootstrapAction()
}

sealed class AppBootstrapDestination {
    data object LaunchScreen : AppBootstrapDestination()
}