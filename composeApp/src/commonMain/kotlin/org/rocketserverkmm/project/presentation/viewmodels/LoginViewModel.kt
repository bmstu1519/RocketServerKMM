package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.models.login.LoginResult
import org.rocketserverkmm.project.domain.usecases.GetLoginUseCase
import org.rocketserverkmm.project.presentation.states.LoginAction
import org.rocketserverkmm.project.presentation.states.LoginDestination
import org.rocketserverkmm.project.presentation.states.LoginState

class LoginViewModel(
    private val getLoginUseCase: GetLoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _destination = MutableSharedFlow<LoginDestination>()
    val destination: SharedFlow<LoginDestination> = _destination

    fun actionToDestination(action: LoginAction) {
        when (action) {
            is LoginAction.InputEmail -> saveToken(action.email)
            LoginAction.ClickSubmit -> goBack()
        }
    }

    private fun saveToken(email: String) {
        viewModelScope.launch {
            val result = getLoginUseCase.login(email)
            when (result.login) {
                null -> handleResult(result)
                else -> {
                    getLoginUseCase.saveToken(token = result.login.token)
                    handleResult(result)
                }

            }
        }
    }

    private fun goBack() {
        viewModelScope.launch {
            _destination.emit(LoginDestination.GoBack)
        }
    }

    private suspend fun handleResult(result: LoginResult) {
        updateState(
            buttonText = "Submit",
            error = result.error
        )
    }

    private suspend fun updateState(
        buttonText: String? = null,
        error: String? = null
    ) {
        _state.update { current ->
            current.copy(
                buttonText = buttonText ?: current.buttonText,
                error = error
            )
        }
    }
}