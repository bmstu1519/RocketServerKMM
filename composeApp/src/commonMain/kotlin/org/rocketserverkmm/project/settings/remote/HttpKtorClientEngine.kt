package org.rocketserverkmm.project.settings.remote

import com.apollographql.apollo.api.http.HttpHeader
import com.apollographql.apollo.api.http.HttpMethod
import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.network.http.HttpEngine
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.util.flattenEntries
import okio.Buffer
import kotlin.coroutines.cancellation.CancellationException

class HttpKtorClientEngine(private val client: HttpClient) : HttpEngine {
    override suspend fun execute(request: HttpRequest): HttpResponse {
        try {
            val response = client.request(request.url) {
                method = when (request.method) {
                    HttpMethod.Get -> io.ktor.http.HttpMethod.Get
                    HttpMethod.Post -> io.ktor.http.HttpMethod.Post
                    else -> throw IllegalArgumentException("Unsupported HTTP method: ${request.method}")
                }
                request.headers.forEach {
                    header(it.name, it.value)
                }
                request.body?.let {
                    header(HttpHeaders.ContentType, it.contentType)
                    val buffer = Buffer()
                    it.writeTo(buffer)
                    setBody(buffer.readUtf8())
                }
            }
            val responseByteArray: ByteArray = response.body()
            val responseBufferedSource = Buffer().write(responseByteArray)
            return HttpResponse.Builder(statusCode = response.status.value)
                .body(responseBufferedSource)
                .addHeaders(
                    response.headers.flattenEntries().map { HttpHeader(it.first, it.second) })
                .build()
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            throw ApolloNetworkException("Network error: ${t.message}", t)
        }
    }
}