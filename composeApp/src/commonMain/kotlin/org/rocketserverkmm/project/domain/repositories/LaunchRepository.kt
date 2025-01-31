package org.rocketserverkmm.project.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.rocketserverkmm.project.TripsBookedSubscription
import org.rocketserverkmm.project.domain.model.launchDetails.LaunchDetailsResult
import org.rocketserverkmm.project.domain.model.launchList.LaunchesResult
import org.rocketserverkmm.project.domain.model.login.LoginResult

interface LaunchRepository {
    suspend fun getLaunches(cursor: String?): LaunchesResult
    suspend fun login(email: String): LoginResult
    suspend fun getLaunchDetails(launchId: String): Result<LaunchDetailsResult>
    suspend fun getTripMutation(launchId: String, isBooked: Boolean): Result<Any>
    suspend fun subscribeToTripBooking(tripsBookedSubscription: TripsBookedSubscription): Result<Flow<String>>
}