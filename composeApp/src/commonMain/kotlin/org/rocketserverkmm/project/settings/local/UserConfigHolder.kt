package org.rocketserverkmm.project.settings.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.rocketserverkmm.project.presentation.states.UserAuthState

class UserConfigHolder {
    private val _state = MutableStateFlow(UserConfigState())
    val state: StateFlow<UserConfigState> = _state.asStateFlow()

    fun updateUserAuthState(authState: UserAuthState?) {
        _state.update { currentState ->
            currentState.copy(isUserAuthorized = authState)
        }
    }

    fun updateThemeState(isDark: Boolean) {
        _state.update { currentState ->
            currentState.copy(isDarkThemeEnabled = isDark)
        }
    }
}

data class UserConfigState(
    var isUserAuthorized: UserAuthState? = null,
    var isDarkThemeEnabled: Boolean = false
)