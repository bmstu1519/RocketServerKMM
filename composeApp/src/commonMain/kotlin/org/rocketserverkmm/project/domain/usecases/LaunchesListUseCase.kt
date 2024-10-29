package org.rocketserverkmm.project.domain.usecases

import org.rocketserverkmm.project.domain.models.LaunchesResult
import org.rocketserverkmm.project.domain.repositories.LaunchRepository

abstract class GetLaunchesUseCase(private val repository: LaunchRepository) {
    abstract suspend operator fun invoke(cursor: String?): LaunchesResult
}