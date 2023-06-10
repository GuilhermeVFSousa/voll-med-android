package com.gvfs.vollmed.features.login.service

import android.content.Context
import com.google.gson.Gson
import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.config.httpInterceptor.AuthInterceptor
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.exceptions.LoginFailException
import com.gvfs.vollmed.features.login.domain.Login
import com.gvfs.vollmed.features.login.domain.TokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.printStack
import io.ktor.utils.io.readUTF8Line
import javax.inject.Inject

@ActivityRetainedScoped
class LoginService @Inject constructor(
    private val authInterceptor: AuthInterceptor,
    @ApplicationContext private val context: Context) {

    private val gson = Gson()
    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }
    private var client = HttpClient{
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }



    suspend fun login(login: String, senha: String) {
        try {
            val request = gson.toJson(Login(login, senha))

            val response = authInterceptor.client.post<HttpResponse> {
                url("${Constants.BASE_URL}${Constants.LOGIN_ENDPOINT}")
                body = TextContent(request, ContentType.Application.Json)
            }
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.content.readUTF8Line()
                val authToken = gson.fromJson(responseBody, TokenResponse::class.java)
                saveToken(authToken.token)
            } else {
                throw LoginFailException()
            }

        } catch (e: ClientRequestException) {
            throw LoginFailException()
        } catch (e: Exception) {
            e.printStack()
            throw HttpResponseErrorException()
        }
    }


  private fun saveToken(token: String) {
      val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      val editor = sharedPreferences.edit()
      editor.putString("token", token)
      editor.apply()
    }
}