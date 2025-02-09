package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.domain.model.launchList.LaunchesResult
import org.rocketserverkmm.project.domain.repositories.LaunchRepository

class GetLaunchesUseCase(private val repository: LaunchRepository) {
    suspend operator fun invoke(cursor: String?): LaunchesResult = repository.getLaunches(cursor)
}