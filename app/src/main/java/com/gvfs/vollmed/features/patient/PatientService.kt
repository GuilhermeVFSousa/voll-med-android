package com.gvfs.vollmed.features.patient

import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.config.httpInterceptor.AuthInterceptor
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.patient.domain.PageablePatient
import com.gvfs.vollmed.features.patient.domain.PatientResume
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.url
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class PatientService @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {

    suspend fun getPatients(): List<PatientResume> {
        try {
            val response = authInterceptor.clientWithAuthorizationToken.get<PageablePatient> {
                url("${Constants.BASE_URL}${Constants.PATIENT_ENDPOINT}")
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