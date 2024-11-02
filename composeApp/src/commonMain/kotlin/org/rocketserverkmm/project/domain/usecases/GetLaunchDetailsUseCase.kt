package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.domain.models.launchDetails.BookTripMutationResult
import org.rocketserverkmm.project.domain.models.launchDetails.LaunchDetailsResult
import org.rocketserverkmm.project.domain.repositories.LaunchRepository

class GetLaunchDetailsUseCase(private val repository: LaunchRepository){
    suspend fun getLaunchDetails(launchId: String) : LaunchDetailsResult = repository.getLaunchDetails(launchId)
    suspend fun tripMutation(launchId: String) : BookTripMutationResult = repository.getTripMutation(launchId)
}