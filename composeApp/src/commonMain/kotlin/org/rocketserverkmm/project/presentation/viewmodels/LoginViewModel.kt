package org.rocketserverkmm.project.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.rocketserverkmm.project.domain.models.login.LoginResult
import org.rocketserverkmm.project.domain.usecases.GetLoginUseCase
import org.rocketserverkmm.project.presentation.states.ButtonState
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

    init {
        viewModelScope.launch {
            updateState(
                error = null
            )
        }
    }

    fun actionToDestination(action: LoginAction) {
        when (action) {
            is LoginAction.ClickSubmit -> saveToken(action.email)
        }
    }

    private fun saveToken(email: String) {
        viewModelScope.launch {
            updateState(
                buttonState = ButtonState.Loading,
            )
            val result = getLoginUseCase.login(email)
            when (result.buttonState) {
                ButtonState.Success -> {
                    result.token?.let { token ->
//                        getLoginUseCase.saveToken(token = token)
                    }
                    delay(1000)
                    handleResult(result)
                    delay(1000)
                    goBack()
                }

                ButtonState.Error -> {
                    delay(1000)
                    handleResult(result)
                    delay(2000)
                    resetButtonState()
                }

                else -> {
                    /* nothing to do */
                }
            }
        }
    }

    private suspend fun resetButtonState() {
        updateState(buttonState = null)
    }

    private suspend fun validateInputEmail(inputEmail: String?) : LoginResult {
//        if (inputEmail == null) {
//            return LoginResult(
//                buttonState = B
//            )
//        }
        TODO()
    }

    private fun matchPattern(inputEmail: String) : String {
        TODO()
    }

    private fun goBack() {
        viewModelScope.launch {
            _destination.emit(LoginDestination.GoBack)
        }
    }

    private suspend fun handleResult(result: LoginResult) {
        updateState(
            buttonState = result.buttonState,
            error = result.error
        )
    }

    private suspend fun updateState(
        buttonState: ButtonState? = null,
        error: String? = null
    ) {
        _state.update { current ->
            current.copy(
                buttonState = buttonState,
                error = error
            )
        }
    }
}