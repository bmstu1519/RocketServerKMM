package org.rocketserverkmm.project.presentation.states

data class LoginState(
    val buttonText: String? = null,
    val error: String? = null
)

sealed class LoginAction {
    data class InputEmail(val email: String) : LoginAction()
    data object ClickSubmit : LoginAction()
}

sealed class LoginDestination {
    data object GoBack : LoginDestination()
}
