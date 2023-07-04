package com.gvfs.vollmed.features.doctor

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.appointment.domain.Appointment
import com.gvfs.vollmed.features.appointment.service.AppointmentService
import com.gvfs.vollmed.features.doctor.domain.DoctorResume
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.utils.io.printStack
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsViewModel @Inject constructor(
    private val service: DoctorService,
    private val appointmentService: AppointmentService
) : ViewModel() {

    private val _doctors = MutableLiveData<List<DoctorResume>>()
    val doctors: LiveData<List<DoctorResume>>get() = _doctors

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDoctors() {
        viewModelScope.launch {
            try {
                _doctors.value = service.getDoctors()
            } catch (e: HttpResponseErrorException) {
                e.printStack()
                emptyList<DoctorResume>()
                return@launch
            }
        }
    }
}