package org.rocketserverkmm.project.domain.repositories

import org.rocketserverkmm.project.domain.models.LaunchList.LaunchesResult
import org.rocketserverkmm.project.domain.models.login.LoginResult

interface LaunchRepository {
    suspend fun getLaunches(cursor: String?): LaunchesResult
    suspend fun login(email: String): LoginResult
}