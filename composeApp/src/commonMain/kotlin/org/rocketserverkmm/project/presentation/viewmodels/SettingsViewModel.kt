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
            SettingsAction.ClickAuthButton -> TODO()
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

    private fun handleResult() {
        updateState(
            settingsState = SettingsState(
                isDarkTheme = TODO(),
                authButtonText = TODO(),
            )
        )
    }

    private fun updateState(
        settingsState: SettingsState? = null,
    ) {
        _state.update { current ->
            current.copy(
                isDarkTheme = settingsState?.isDarkTheme ?: current.isDarkTheme,
                authButtonText = settingsState?.authButtonText ?: current.authButtonText
            )
        }
    }
}