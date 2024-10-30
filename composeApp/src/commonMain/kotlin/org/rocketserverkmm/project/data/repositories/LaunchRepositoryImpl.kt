package org.rocketserverkmm.project.data.repositories

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import org.rocketserverkmm.project.LaunchListQuery
import org.rocketserverkmm.project.domain.models.LaunchesResult
import org.rocketserverkmm.project.domain.models.toDomain
import org.rocketserverkmm.project.domain.repositories.LaunchRepository

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
}