package org.rocketserverkmm.project.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.HttpNetworkTransport
import kotlinx.coroutines.delay
import org.rocketserverkmm.project.dependencies.DependencyProvider

object ProvideApolloClientSingleton {
    internal val apolloClient: ApolloClient by lazy {
        ApolloClient.Builder()
            .networkTransport(
                HttpNetworkTransport.Builder()
                    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
                    .httpEngine(DependencyProvider.ktorClient)
                    .build()
            )
            .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .webSocketReopenWhen { throwable, attempt ->
                println("Apollo: WebSocket got disconnected, reopening after a delay")
                println("Throwable:$throwable")
                delay(attempt * 1000)
                true
            }
            .build()
    }
}
