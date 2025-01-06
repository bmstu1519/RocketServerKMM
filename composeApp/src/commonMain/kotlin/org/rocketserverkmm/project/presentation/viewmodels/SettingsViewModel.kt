package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.usecases.GetSettingsUseCase
import org.rocketserverkmm.project.presentation.states.ActionableAlert
import org.rocketserverkmm.project.presentation.states.ActionableButton
import org.rocketserverkmm.project.presentation.states.AuthResult
import org.rocketserverkmm.project.presentation.states.SettingsAction
import org.rocketserverkmm.project.presentation.states.SettingsDestination
import org.rocketserverkmm.project.presentation.states.SettingsState
import org.rocketserverkmm.project.presentation.states.UserAuthState
import org.rocketserverkmm.project.settings.local.UserConfigHolder

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val userConfigHolder: UserConfigHolder
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    private val _destination = MutableSharedFlow<SettingsDestination>()
    val destination: SharedFlow<SettingsDestination> = _destination

    fun actionToDestination(action: SettingsAction) {
        when (action) {
            SettingsAction.OpenScreen -> getUserSettings()
            SettingsAction.ChangeTheme -> changeTheme()
            SettingsAction.ClickAuthButton -> handleAuthClick()
            SettingsAction.ShowAlert -> showAlert()
            SettingsAction.ClickAuthButton.LogIn -> TODO()
            SettingsAction.ClickAuthButton.LogOut -> handleLogOut()
        }
    }

    private fun getUserSettings() {
        viewModelScope.launch {
            updateState(
                settingsState = SettingsState(
                    userAuthState = userConfigHolder.state.value.isUserAuthorized,
                    isDarkTheme = userConfigHolder.state.value.isDarkThemeEnabled
                )
            )
        }
    }

    private fun handleLogOut() {
        viewModelScope.launch {
            getSettingsUseCase.logOut()
            updateState(
                settingsState = SettingsState(
                    userAuthState = UserAuthState.NON_AUTHORIZED,
                )
            )
            userConfigHolder.updateUserAuthState(UserAuthState.NON_AUTHORIZED)
        }
    }

    private fun changeTheme() {
        viewModelScope.launch {
            val isDark = getSettingsUseCase.changeTheme(_state.value.isDarkTheme!!)
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
                AuthResult.RequiresLogout -> actionToDestination(SettingsAction.ShowAlert)
                is AuthResult.Error -> updateState(
                    settingsState = SettingsState(
                        error = result.message
                    )
                )
            }
        }
    }

    private fun showAlert(){
        viewModelScope.launch {
            updateState(
                settingsState = SettingsState(
                    actionableAlert = prepareAlert()
                )
            )
            _destination.emit(SettingsDestination.ShowAlert)
        }
    }

    private fun prepareAlert() : ActionableAlert = ActionableAlert(
        text = "Вы действительно хотите выйти?",
        submitButton = ActionableButton(
            buttonText = "Выйти из аккаунта",
            action = { actionToDestination(SettingsAction.ClickAuthButton.LogOut) },
        ),
        cancelButton = ActionableButton(
            buttonText = "Остаться",
            action = { },
        ),
    )

    private fun updateState(
        settingsState: SettingsState? = null,
    ) {
        _state.update { current ->
            current.copy(
                isLoading = settingsState?.isLoading ?: current.isLoading,
                isDarkTheme = settingsState?.isDarkTheme ?: current.isDarkTheme,
                authButtonText = settingsState?.authButtonText ?: current.authButtonText,
                actionableAlert = settingsState?.actionableAlert ?: current.actionableAlert,
                userAuthState = settingsState?.userAuthState ?: current.userAuthState,
                error = settingsState?.error ?: current.error,
            )
        }
    }
}