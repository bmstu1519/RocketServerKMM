package org.rocketserverkmm.project.presentation.states

data class LoginState(
    val buttonText: String,
    val error: String? = null
)

sealed class LoginAction {
    data object ClickSubmit : LoginAction()
    data class InputEmail(val email: String) : LoginAction()
}

sealed class LoginDestination {
    data object GoBack : LoginDestination()
}
