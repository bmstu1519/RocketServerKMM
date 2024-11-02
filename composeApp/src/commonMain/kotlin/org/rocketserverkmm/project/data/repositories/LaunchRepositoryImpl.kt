package org.rocketserverkmm.project.data.repositories

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import org.rocketserverkmm.project.LaunchListQuery
import org.rocketserverkmm.project.LoginMutation
import org.rocketserverkmm.project.domain.models.launchDetails.BookTripMutationResult
import org.rocketserverkmm.project.domain.models.launchDetails.LaunchDetailsResult
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

    override suspend fun getLaunchDetails(launchId: String): LaunchDetailsResult {
        TODO("Not yet implemented")
    }

    override suspend fun getTripMutation(launchId: String): BookTripMutationResult {
        TODO("Not yet implemented")
    }
}