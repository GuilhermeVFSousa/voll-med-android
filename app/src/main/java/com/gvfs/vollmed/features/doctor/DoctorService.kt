package com.gvfs.vollmed.features.doctor

import android.os.Build
import androidx.annotation.RequiresApi
import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.config.httpInterceptor.AuthInterceptor
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.doctor.domain.DoctorCreate
import com.gvfs.vollmed.features.doctor.domain.DoctorResume
import com.gvfs.vollmed.features.doctor.domain.PageableDoctor
import com.gvfs.vollmed.features.patient.domain.PatientCreate
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.utils.io.printStack
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class DoctorService @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {
    @RequiresApi(Build.VERSION_CODES.O)
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
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createDoctor(doctor: DoctorCreate): Boolean {
        return try {
            authInterceptor.clientWithAuthorizationToken.post<DoctorCreate> {
                url("${Constants.BASE_URL}${Constants.DOCTOR_CREATE_ENDPOINT}")
                body = doctor
            }
            true
        } catch (e: ClientRequestException) {
            e.printStack()
            false
        } catch (e: Exception) {
            e.printStack()
            false
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getSpecialty(): List<String> {
        try {
            val response = authInterceptor.clientWithAuthorizationToken.get<List<String>> {
                url("${Constants.BASE_URL}${Constants.DOCTOR_SPECIALTY_ENDPOINT}")
            }
            println("::::::::::::::::::: $response")

            return response.map { it.uppercase() };
        } catch (e: ClientRequestException) {
            throw HttpResponseErrorException()
        } catch (e: Exception) {
            throw HttpResponseErrorException()
        }
    }

}