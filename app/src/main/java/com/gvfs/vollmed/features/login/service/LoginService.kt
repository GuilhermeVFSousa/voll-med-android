package com.gvfs.vollmed.features.login.service

import com.google.gson.Gson
import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.exceptions.LoginFailException
import com.gvfs.vollmed.features.login.domain.Login
import com.gvfs.vollmed.features.login.domain.TokenResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.readUTF8Line
import javax.inject.Inject

@ActivityRetainedScoped
class LoginService @Inject constructor(private val httpClient: HttpClient) {

    private val gson = Gson()
    private var authToken: String? = null

    suspend fun login(login: String, senha: String): Any {
        try {
            val request = gson.toJson(Login(login, senha))
            val response = httpClient.post<HttpResponse> {
                url("${Constants.BASE_URL}${Constants.LOGIN_ENDPOINT}")
                body = TextContent(request, ContentType.Application.Json)
            }
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.content.readUTF8Line()
                println("::::::::::::::::::: $responseBody")
                val authToken = gson.fromJson(responseBody, TokenResponse::class.java)
                println("::::::::::::::::::: ${authToken.token}")
            } else {
                throw LoginFailException()
            }

        } catch (e: ClientRequestException) {
            throw LoginFailException()
        } catch (e: Exception) {
            throw HttpResponseErrorException()
        }
        return this
    }

    fun getToken(): String? {
        return authToken
    }
}