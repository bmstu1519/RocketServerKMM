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
                loginState = LoginState(
                    error = null
                )
            )
        }
    }

    fun actionToDestination(action: LoginAction) {
        when (action) {
            is LoginAction.ClickSubmit -> validateInputEmail(action.inputEmail)
        }
    }

    private fun validateInputEmail(inputEmail: String?) {
        viewModelScope.launch {
            when (inputEmail) {
                null -> {
                    handleResult(LoginResult(buttonState = ButtonState.Error))
                    ButtonState.Error.handleStateChange(
                        2000L
                    ) {
                        updateState(
                            loginState = LoginState(
                                buttonState = null
                            )
                        )
                    }
                }

                else -> {
                    if (matchPattern(inputEmail)) {
                        handleResult(LoginResult(buttonState = ButtonState.Loading))
                        saveToken(inputEmail)
                    } else {
                        handleResult(LoginResult(buttonState = ButtonState.Error))
                        ButtonState.Error.handleStateChange(
                            2000L
                        ) {
                            resetButtonState()
                        }
                    }
                }
            }
        }
    }

    private suspend fun saveToken(email: String) {
        val result = getLoginUseCase.login(email)
        when (result.buttonState) {
            ButtonState.Success -> {
                result.token?.let { token ->
                        getLoginUseCase.saveToken(token = token)
                }
                handleResult(result)
                ButtonState.Success.handleStateChange(
                    2000L
                ) {
                    resetButtonState()
                }
                goBack()
            }

            ButtonState.Error -> {
                handleResult(result)
                ButtonState.Error.handleStateChange(
                    2000L
                ) {
                    resetButtonState()
                }
            }

            else -> {
                /* nothing to do */
            }
        }
    }

    private fun resetButtonState() {
        updateState(
            loginState = LoginState(
                buttonState = null
            )
        )
    }

    private fun matchPattern(inputEmail: String): Boolean =
        Constants.EMAIL_REGEX.matches(inputEmail)

    private fun goBack() {
        viewModelScope.launch {
            _destination.emit(LoginDestination.GoBack)
        }
    }

    private fun handleResult(result: LoginResult) {
        updateState(
            loginState = LoginState(
                buttonState = result.buttonState,
                error = result.error
            )
        )
    }

    private fun updateState(
        loginState: LoginState? = null,
    ) {
        _state.update { current ->
            current.copy(
                buttonState = loginState?.buttonState, //?: current.buttonState,
                error = loginState?.error ?: current.error
            )
        }
    }
}