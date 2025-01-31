package org.rocketserverkmm.project.domain.model.login

import org.rocketserverkmm.project.presentation.states.ButtonState

data class LoginResult(
    val buttonState: ButtonState? = null,
    val token: String? = null,
    val error: String? = null
)
