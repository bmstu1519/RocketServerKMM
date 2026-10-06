package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.usecases.GetAppBootstrapUseCase
import org.rocketserverkmm.project.presentation.states.AppBootstrapAction
import org.rocketserverkmm.project.presentation.states.AppBootstrapState
import org.rocketserverkmm.project.presentation.states.UserAuthState
import org.rocketserverkmm.project.data.local.UserConfigHolder

class AppBootstrapViewModel(
    private val getAppBootstrapUseCase: GetAppBootstrapUseCase,
    private val userConfigHolder: UserConfigHolder
) : ViewModel() {
    private val _state = MutableStateFlow(AppBootstrapState(isUserAuthorized = UserAuthState.NO_IMPLEMENTATION))
    val state: StateFlow<AppBootstrapState> = _state.asStateFlow()

    fun initialize() {
        actionToDestination(AppBootstrapAction.Load)
    }

    private fun actionToDestination(action: AppBootstrapAction) {
        when (action) {
            AppBootstrapAction.Load -> handleFirstLoad()
        }
    }

    private fun handleFirstLoad() {
        viewModelScope.launch {
            val authDeferred = async { getAppBootstrapUseCase.getUserAuth() }
            val themeDeferred = async { getAppBootstrapUseCase.getCurrentTheme() }
            val (authState, theme) = Pair(authDeferred.await(), themeDeferred.await())

            unitUserConfig(
                AppBootstrapState(
                    isUserAuthorized = authState,
                    isDarkThemeEnabled = theme,
                )
            )
        }
    }


    private fun unitUserConfig(
        appBootstrapState: AppBootstrapState,
    ) {
        userConfigHolder.updateUserAuthState(appBootstrapState.isUserAuthorized)
        userConfigHolder.updateThemeState(appBootstrapState.isDarkThemeEnabled)
    }
}