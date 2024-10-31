package org.rocketserverkmm.project.domain.models.login

data class LoginResult(
    val login: LoginDto
)

data class LoginDto(
    val token: String? = null
)
