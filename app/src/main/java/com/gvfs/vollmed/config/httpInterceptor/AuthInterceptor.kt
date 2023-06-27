package com.gvfs.vollmed.config.httpInterceptor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.utils.serializer.LocalDateTimeSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.HttpClient
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import java.time.LocalDateTime
import javax.inject.Inject

@ActivityRetainedScoped
class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    var clientWithAuthorizationToken = HttpClient{
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(DefaultRequest) {
            header("Authorization", "Bearer ${getToken()}")
        }
        install(HttpTimeout) {
            requestTimeoutMillis = Constants.HTTP_REQUEST_TIMEOUT
            connectTimeoutMillis = Constants.HTTP_REQUEST_TIMEOUT
            socketTimeoutMillis = Constants.HTTP_REQUEST_TIMEOUT
        }
        defaultRequest {
            if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    var client = HttpClient{
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = Constants.HTTP_REQUEST_TIMEOUT
            connectTimeoutMillis = Constants.HTTP_REQUEST_TIMEOUT
            socketTimeoutMillis = Constants.HTTP_REQUEST_TIMEOUT
        }
    }

    private fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.TOKEN_KEY, null)
    }

}