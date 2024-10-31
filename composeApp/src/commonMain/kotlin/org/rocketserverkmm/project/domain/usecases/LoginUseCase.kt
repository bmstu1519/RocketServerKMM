package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.domain.models.login.LoginResult
import org.rocketserverkmm.project.domain.repositories.LaunchRepository

class LoginUseCase(private val repository: LaunchRepository) {
    suspend fun invoke(email: String) : LoginResult = repository.login(email)
}