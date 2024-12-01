package org.rocketserverkmm.project.di.modules

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.HttpEngine
import com.apollographql.apollo.network.http.HttpNetworkTransport
import com.liftric.kvault.KVault
import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.delay
import org.koin.dsl.bind
import org.koin.dsl.module
import org.rocketserverkmm.project.domain.repositories.KeyVaultRepository
import org.rocketserverkmm.project.platform.KEY_TOKEN
import org.rocketserverkmm.project.platform.getEngine
import org.rocketserverkmm.project.platform.getKVaultInstance
import org.rocketserverkmm.project.settings.remote.HttpKtorClientEngine

val clientsModule = module {
    single<HttpEngine> {
        val ktorClient: HttpClient = get()

        HttpKtorClientEngine(ktorClient)
    } bind HttpEngine::class

    single<KVault> {
        getKVaultInstance().kVault
    } bind KVault::class

    single<HttpClient> {
        val keyVaultRepository: KeyVaultRepository = get()

        getEngine().createHttpClient {

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            defaultRequest {
                header(HttpHeaders.Accept, "application/json")
                header(HttpHeaders.ContentType, "application/json")
            }
        }.apply {
            requestPipeline.intercept(HttpRequestPipeline.Before) {
                val token = keyVaultRepository.getToken(KEY_TOKEN)
                if (!token.isNullOrEmpty()) {
                    context.headers[HttpHeaders.Authorization] = token
                }
            }
        }
    } bind HttpClient::class

    single<ApolloClient> {
        val ktorEngine: HttpEngine = get()

        ApolloClient.Builder()
            .networkTransport(
                HttpNetworkTransport.Builder()
                    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
                    .httpEngine(ktorEngine)
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
    } bind ApolloClient::class
}