package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.usecases.AppBootstrapUseCase
import org.rocketserverkmm.project.presentation.states.AppBootstrapAction
import org.rocketserverkmm.project.presentation.states.AppBootstrapDestination
import org.rocketserverkmm.project.presentation.states.AppBootstrapState
import org.rocketserverkmm.project.presentation.states.UserAuthState

class AppBootstrapViewModel(
    private val getAppBootstrapUseCase: AppBootstrapUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AppBootstrapState())
    val state: StateFlow<AppBootstrapState> = _state

    private val _destination = MutableSharedFlow<AppBootstrapDestination>()
    val destination: SharedFlow<AppBootstrapDestination> = _destination

    fun actionToDestination(action: AppBootstrapAction) {
        when (action) {
            AppBootstrapAction.Load -> TODO()
        }
    }

    private fun handleFirstLoad() {
        viewModelScope.launch {
            val userAuthState = getAppBootstrapUseCase.getUserAuth()

            when (userAuthState) {
                UserAuthState.NON_AUTHORIZED -> updateState(
                    appBootstrapState = AppBootstrapState(
                        isUserAuthorized = UserAuthState.NON_AUTHORIZED,
                        isDarkThemeEnabled = false,
                    )
                )

                UserAuthState.AUTHORIZED -> {
                    val isDarkThemeEnabled = getAppBootstrapUseCase.getCurrentTheme()
                    updateState(
                        appBootstrapState = AppBootstrapState(
                            isUserAuthorized = UserAuthState.AUTHORIZED,
                            isDarkThemeEnabled = isDarkThemeEnabled,
                        )
                    )
                }
            }
        }
    }


    private fun updateState(
        appBootstrapState: AppBootstrapState? = null,
    ) {
        _state.update { current ->
            current.copy(
                isUserAuthorized = appBootstrapState?.isUserAuthorized ?: current.isUserAuthorized,
                isDarkThemeEnabled = appBootstrapState?.isDarkThemeEnabled ?: current.isDarkThemeEnabled,
            )
        }
    }
}