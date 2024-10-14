package org.rocketserverkmm.project

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.HttpNetworkTransport

object ProvideApolloClientSingleton {
    internal val apolloClient: ApolloClient by lazy {
        ApolloClient.Builder()
            .networkTransport(
                HttpNetworkTransport.Builder()
                    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
                    .httpEngine(ProvideKtorClientSingleton.getInstance())
                    .build()
            ).build()
    }
}
