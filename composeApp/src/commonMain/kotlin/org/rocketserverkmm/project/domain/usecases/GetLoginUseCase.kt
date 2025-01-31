package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.platform.KEY_TOKEN
import org.rocketserverkmm.project.domain.model.login.LoginResult
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.LaunchRepository

class GetLoginUseCase(
    private val repository: LaunchRepository,
    private val keyVaultRepository: KeyVaultRepository
) {
    suspend fun login(email: String) : LoginResult = repository.login(email)
    fun saveToken(key: String = KEY_TOKEN, token: String) = keyVaultRepository.saveToken(key, token)
}