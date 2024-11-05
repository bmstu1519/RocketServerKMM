package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.KEY_TOKEN
import org.rocketserverkmm.project.domain.models.launchDetails.LaunchDetailsResult
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.domain.repositories.LaunchRepository

class GetLaunchDetailsUseCase(
    private val repository: LaunchRepository,
    private val keyVaultRepository: KeyVaultRepository
) {
    suspend fun getLaunchDetails(launchId: String): Result<LaunchDetailsResult> =
        repository.getLaunchDetails(launchId)

    fun checkToken(key: String = KEY_TOKEN): Boolean = keyVaultRepository.getToken(key) !== null

    suspend fun tripMutation(launchId: String, isBooked: Boolean): Result<Any> =
        repository.getTripMutation(launchId, isBooked)
}