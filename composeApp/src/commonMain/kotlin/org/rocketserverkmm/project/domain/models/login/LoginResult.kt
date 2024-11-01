package org.rocketserverkmm.project.domain.models.login

import org.rocketserverkmm.project.presentation.states.ButtonState

data class LoginResult(
    val buttonState: ButtonState? = null,
    val token: String? = null,
    val error: String? = null
)
