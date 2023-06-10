package com.gvfs.vollmed.features.doctor

import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.config.httpInterceptor.AuthInterceptor
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.doctor.domain.DoctorResume
import com.gvfs.vollmed.features.doctor.domain.PageableDoctor
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.url
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class DoctorService @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {
    suspend fun getDoctors() : List<DoctorResume> {
        try {
            val response = authInterceptor.clientWithAuthorizationToken.get<PageableDoctor> {
                url("${Constants.BASE_URL}${Constants.DOCTOR_ENDPOINT}")
            }
            println("::::::::::::::::::: ${response.content}")
            return response.content
        } catch (e: ClientRequestException) {
            throw HttpResponseErrorException()
        } catch (e: Exception) {
            throw HttpResponseErrorException()
        }
    }

}