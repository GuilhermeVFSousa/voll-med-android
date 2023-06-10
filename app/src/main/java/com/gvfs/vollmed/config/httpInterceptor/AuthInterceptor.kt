package com.gvfs.vollmed.config.httpInterceptor

import android.content.Context
import com.gvfs.vollmed.config.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.HttpClient
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import javax.inject.Inject

@ActivityRetainedScoped
class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    var clientWithAuthorizationToken = HttpClient{
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(DefaultRequest) {
            header("Authorization", "Bearer ${getToken()}")
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
    }

    var client = HttpClient{
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
    }

    private fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.TOKEN_KEY, null)
    }

}