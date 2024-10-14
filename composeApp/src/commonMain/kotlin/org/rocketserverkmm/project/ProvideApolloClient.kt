package org.rocketserverkmm.project

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import com.apollographql.apollo.network.http.HttpNetworkTransport
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.flow.Flow

class ProvideApolloClient {
    internal val apolloClient: ApolloClient = ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder()
                .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
                .build()
        )
        .addHttpHeader("Accept","application/json")
        .addHttpHeader("Content-Type","application/json")
        .build()
}

class AuthorizationInterceptor : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val token = KVaultSettingsProviderSingleton.getInstance().getToken(KEY_TOKEN)
        val newRequest = if (token != null) {
            request.newBuilder().addHttpHeader("Authorization", "Bearer $token").build()
        } else {
            request
        }
        return chain.proceed(newRequest)
    }
}