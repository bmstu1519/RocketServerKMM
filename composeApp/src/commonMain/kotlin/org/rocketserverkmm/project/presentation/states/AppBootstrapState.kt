package org.rocketserverkmm.project.presentation.states

data class AppBootstrapState(
    val isLoading: Boolean? = null,
    val isUserAuthorized: UserAuthState? = null,
    val isDarkThemeEnabled: Boolean = false
)

enum class UserAuthState {
    NON_AUTHORIZED,
    AUTHORIZED
}

sealed class AppBootstrapAction {
    data object Load : AppBootstrapAction()
    data object LaunchScreen: AppBootstrapAction()
}

sealed class AppBootstrapDestination {
    data object LaunchScreen : AppBootstrapDestination()
}