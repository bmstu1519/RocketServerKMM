package org.rocketserverkmm.project.domain.models.login

data class LoginResult(
    val login: LoginDto? = null,
    val error: String? = null
)

data class LoginDto(
    val token: String
)
