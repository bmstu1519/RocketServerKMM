package org.rocketserverkmm.project.platform

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import org.rocketserverkmm.project.platform.EngineProvider

object EngineProviderIOS : EngineProvider {
    override fun createHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient(Darwin) {
            block()
        }
    }
}

actual fun getEngine() : EngineProvider = EngineProviderIOS