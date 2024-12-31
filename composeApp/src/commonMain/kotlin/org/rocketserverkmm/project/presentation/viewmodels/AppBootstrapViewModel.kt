package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.usecases.GetAppBootstrapUseCase
import org.rocketserverkmm.project.presentation.states.AppBootstrapAction
import org.rocketserverkmm.project.presentation.states.AppBootstrapDestination
import org.rocketserverkmm.project.presentation.states.AppBootstrapState

class AppBootstrapViewModel(
    private val getAppBootstrapUseCase: GetAppBootstrapUseCase,
//    private val data: FirstLoadInitialData
) : ViewModel() {
    private val _state = MutableStateFlow(AppBootstrapState(isLoading = true))
    val state: StateFlow<AppBootstrapState> = _state

    private val _destination = MutableSharedFlow<AppBootstrapDestination>()
    val destination: SharedFlow<AppBootstrapDestination> = _destination

    fun actionToDestination(action: AppBootstrapAction) {
        when (action) {
            AppBootstrapAction.Load -> handleFirstLoad()
            AppBootstrapAction.LaunchScreen -> launchScreen()
        }
    }

    private fun launchScreen() {
        viewModelScope.launch {
            _destination.emit(AppBootstrapDestination.LaunchScreen)
        }
    }

    private fun handleFirstLoad() {
        viewModelScope.launch {
            val authDeferred = async { getAppBootstrapUseCase.getUserAuth() }
            val themeDeferred = async { getAppBootstrapUseCase.getCurrentTheme() }
            val (authState, theme) = Pair(authDeferred.await(), themeDeferred.await())

            updateState(
                AppBootstrapState(
                    isLoading = false,
                    isUserAuthorized = authState,
                    isDarkThemeEnabled = theme ,
                )
            )
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