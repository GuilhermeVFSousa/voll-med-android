package com.gvfs.vollmed.features.appointment

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.extension.getBrazilianFormatString
import com.gvfs.vollmed.features.appointment.domain.Appointment
import com.gvfs.vollmed.features.appointment.service.AppointmentService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.utils.io.printStack
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val service: AppointmentService
) : ViewModel() {
    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>>get() = _appointments

    fun getAppointments(idDoctor: Long, date: String) {
        viewModelScope.launch {
            try {
                _appointments.value = service.getAppointments(1, "2023-06-05")
            } catch (e: HttpResponseErrorException) {
                emptyList<Appointment>()
                return@launch
            }
        }
    }

    fun getFormattedDate(date: LocalDateTime): String {
        println("::::::::::::::::::::::::::$date")
        return date.getBrazilianFormatString(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun getAppointmentDuration(startDate: LocalDateTime, finalDate: LocalDateTime): String {
//        var duration = startDate.minus(finalDate)
//    }
}