package org.rocketserverkmm.project.domain.repositories

import org.rocketserverkmm.project.domain.models.launchDetails.BookTripMutationResult
import org.rocketserverkmm.project.domain.models.launchDetails.LaunchDetailsResult
import org.rocketserverkmm.project.domain.models.launchList.LaunchesResult
import org.rocketserverkmm.project.domain.models.login.LoginResult

interface LaunchRepository {
    suspend fun getLaunches(cursor: String?): LaunchesResult
    suspend fun login(email: String): LoginResult
    suspend fun getLaunchDetails(launchId: String): Result<LaunchDetailsResult>
    suspend fun getTripMutation(launchId: String): BookTripMutationResult
}