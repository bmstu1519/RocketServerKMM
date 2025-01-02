package org.rocketserverkmm.project.presentation.states

data class AppBootstrapState(
    val isLoading: Boolean? = null,
    val isUserAuthorized: UserAuthState = UserAuthState.NO_IMPLEMENTATION,
    val isDarkThemeEnabled: Boolean = false
)

enum class UserAuthState {
    NON_AUTHORIZED,
    AUTHORIZED,
    NO_IMPLEMENTATION
}

sealed class AppBootstrapAction {
    data object Load : AppBootstrapAction()
    data object LaunchScreen: AppBootstrapAction()
}

sealed class AppBootstrapDestination {
    data object LaunchScreen : AppBootstrapDestination()
}