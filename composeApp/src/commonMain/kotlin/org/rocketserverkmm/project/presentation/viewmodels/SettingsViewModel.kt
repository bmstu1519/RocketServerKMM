package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.usecases.GetSettingsUseCase
import org.rocketserverkmm.project.presentation.states.AuthResult
import org.rocketserverkmm.project.presentation.states.SettingsAction
import org.rocketserverkmm.project.presentation.states.SettingsDestination
import org.rocketserverkmm.project.presentation.states.SettingsState

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    private val _destination = MutableSharedFlow<SettingsDestination>()
    val destination: SharedFlow<SettingsDestination> = _destination

    fun actionToDestination(action: SettingsAction) {
        when (action) {
            SettingsAction.ChangeTheme -> changeTheme()
            SettingsAction.ClickAuthButton -> handleAuthClick()
        }
    }

    private fun changeTheme() {
        viewModelScope.launch {
            val isDark = getSettingsUseCase.changeTheme(_state.value.isDarkTheme)
            updateState(
                settingsState = SettingsState(
                    isDarkTheme = isDark
                )
            )
        }
    }

    private fun handleAuthClick() {
        viewModelScope.launch {
            val result = getSettingsUseCase.clickAuth()
            when (result) {
                AuthResult.RequiresLogin -> _destination.emit(SettingsDestination.GoToLogin)
                AuthResult.RequiresLogout -> _destination.emit(SettingsDestination.ShowAlert)
                is AuthResult.Error -> updateState(
                    settingsState = SettingsState(
                        error = result.message
                    )
                )
            }
        }
    }

    private fun updateState(
        settingsState: SettingsState? = null,
    ) {
        _state.update { current ->
            current.copy(
                isLoading = settingsState?.isLoading ?: current.isLoading,
                isDarkTheme = settingsState?.isDarkTheme ?: current.isDarkTheme,
                authButtonText = settingsState?.authButtonText ?: current.authButtonText,
                error = settingsState?.error ?: current.error,
            )
        }
    }
}