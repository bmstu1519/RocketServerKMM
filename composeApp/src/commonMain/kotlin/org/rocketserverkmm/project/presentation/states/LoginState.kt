package org.rocketserverkmm.project.presentation.states

data class LoginState(
    val buttonState: ButtonState? = null,
    val titleText: String = "Login",
    val labelText: String = "Email",
    val buttonText: String = "Submit",
    val error: String? = null
)

sealed class LoginAction {
    data class ClickSubmit(val email: String) : LoginAction()
}

sealed class LoginDestination {
    data object GoBack : LoginDestination()
}
