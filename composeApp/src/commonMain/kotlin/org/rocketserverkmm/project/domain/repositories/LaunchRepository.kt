package org.rocketserverkmm.project.domain.repositories

import org.rocketserverkmm.project.domain.models.LaunchList.LaunchesResult

interface LaunchRepository {
    suspend fun getLaunches(cursor: String?): LaunchesResult
}