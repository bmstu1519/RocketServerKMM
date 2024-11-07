package org.rocketserverkmm.project.data.repositories

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.rocketserverkmm.project.BookTripMutation
import org.rocketserverkmm.project.CancelTripMutation
import org.rocketserverkmm.project.LaunchDetailsQuery
import org.rocketserverkmm.project.LaunchListQuery
import org.rocketserverkmm.project.LoginMutation
import org.rocketserverkmm.project.TripsBookedSubscription
import org.rocketserverkmm.project.dependencies.DependencyProvider
import org.rocketserverkmm.project.domain.models.launchDetails.LaunchDetailsResult
import org.rocketserverkmm.project.domain.models.launchDetails.MissionDTO
import org.rocketserverkmm.project.domain.models.launchDetails.RocketDTO
import org.rocketserverkmm.project.domain.models.launchList.LaunchesResult
import org.rocketserverkmm.project.domain.models.launchList.toDomain
import org.rocketserverkmm.project.domain.models.login.LoginResult
import org.rocketserverkmm.project.domain.repositories.LaunchRepository
import org.rocketserverkmm.project.presentation.states.ButtonState

class LaunchRepositoryImpl(
    private val apolloClient: ApolloClient
) : LaunchRepository {
    override suspend fun getLaunches(cursor: String?): LaunchesResult {
        val response = apolloClient.query(LaunchListQuery(Optional.present(cursor))).execute()
        return response.data?.launches?.let { launches ->
            LaunchesResult(
                launches = launches.launches.filterNotNull()
                    .map { launch: LaunchListQuery.Launch -> launch.toDomain() },
                hasMore = launches.hasMore,
                cursor = launches.cursor
            )
        } ?: LaunchesResult(emptyList(), false, null)
    }

    override suspend fun login(email: String): LoginResult {
        val response = apolloClient.mutation(LoginMutation(email = email)).execute()
        return response.data?.let { mutationResult ->
            mutationResult.login?.let { login ->
                login.token?.let {
                    LoginResult(
                        buttonState = ButtonState.Success,
                        token = login.token
                    )
                } ?: LoginResult(
                    buttonState = ButtonState.Error,
                    error = "Login: Failed to login: no token returned by the backend"
                )
            } ?: LoginResult(
                buttonState = ButtonState.Error,
                error = "Login: Failed to login: ${response.errors!![0].message}"
            )
        } ?: LoginResult(
            buttonState = ButtonState.Error,
            error = "Login: Failed to login ${response.exception}"
        )
    }

    override suspend fun getLaunchDetails(launchId: String): Result<LaunchDetailsResult> {
        return runCatching {
            val response =
                DependencyProvider.apolloClient.query(LaunchDetailsQuery(launchId)).execute()

            LaunchDetailsResult(
                id = response.data?.launch?.id,
                site = response.data?.launch?.site,
                mission = MissionDTO(response.data?.launch?.mission),
                rocket = RocketDTO(response.data?.launch?.rocket),
                isBooked = response.data?.launch?.isBooked,
            )
        }
    }

    override suspend fun getTripMutation(launchId: String, isBooked: Boolean): Result<Any> {
        return runCatching {
            val mutation = if (isBooked) {
                CancelTripMutation(id = launchId)
            } else {
                BookTripMutation(id = launchId)
            }
            DependencyProvider.apolloClient.mutation(mutation).execute()
        }
    }

    override suspend fun subscribeToTripBooking(tripsBookedSubscription: TripsBookedSubscription): Result<Flow<String>> {
        return kotlin.runCatching {
            val response = DependencyProvider.apolloClient.subscription(TripsBookedSubscription())
            response.toFlow()
                .map {
                    when (it.data?.tripsBooked) {
                        null -> "Subscription error"
                        -1 -> "Trip cancelled"
                        else -> "Trip booked! 🚀"
                    }
                }
        }
    }
}