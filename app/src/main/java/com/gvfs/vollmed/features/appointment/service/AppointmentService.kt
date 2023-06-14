package com.gvfs.vollmed.features.appointment.service

import com.gvfs.vollmed.config.Constants
import com.gvfs.vollmed.config.httpInterceptor.AuthInterceptor
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.appointment.domain.Appointment
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.utils.io.printStack
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class AppointmentService @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {

    suspend fun getAppointments(idDoctor: Long, appointmentDate: String): List<Appointment> {
        try {
            val response = authInterceptor.clientWithAuthorizationToken.get<List<Appointment>> {
                url("${Constants.BASE_URL}${Constants.APPOINTMENT_ENDPOINT}/medico/$idDoctor/data/$appointmentDate")
            }
            println("::::::::::::::::::: $response")
        return response
        } catch (e: ClientRequestException) {
            throw HttpResponseErrorException()
        } catch (e: Exception) {
            throw HttpResponseErrorException()
        }
    }
}