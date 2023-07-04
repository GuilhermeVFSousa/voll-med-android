package com.gvfs.vollmed.features.doctorcreate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.alert.AlertEvent
import com.gvfs.vollmed.features.doctor.DoctorService
import com.gvfs.vollmed.features.doctor.domain.DoctorCreate
import com.gvfs.vollmed.features.doctor.domain.DoctorResume
import com.gvfs.vollmed.features.patient.PatientService
import com.gvfs.vollmed.features.shareddomain.Endereco
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.utils.io.printStack
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorCreateViewModel @Inject constructor(
    private val service: DoctorService,
    private val patientService: PatientService
): ViewModel() {
    private val _address = MutableLiveData<Endereco>()
    val address: LiveData<Endereco> get() = _address

    private val _specialties = MutableLiveData<List<String>>()
    val specialties: LiveData<List<String>>get() = _specialties
    val events: MutableLiveData<AlertEvent> = MutableLiveData()
    val createEvent: MutableLiveData<AlertEvent> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSpecialties() {
        viewModelScope.launch {
            try {
                val specialtyList = mutableListOf<String>()
                specialtyList.add("Selecione uma especialidade")
                specialtyList.addAll(service.getSpecialty())
                _specialties.value = specialtyList
            } catch (e: HttpResponseErrorException) {
                e.printStack()
                emptyList<String>()
                return@launch
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun create(doctorCreate: DoctorCreate) {
        viewModelScope.launch {
            val response = service.createDoctor(doctorCreate)
            if (response) createEvent.value = AlertEvent.DoctorCreated()
            else createEvent.value = AlertEvent.DoctorCreatedError()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCep(cep: String) {
        viewModelScope.launch {
            try {
                _address.value = patientService.getCEP(cep)
                if(_address.value?.cep == "") events.value = AlertEvent.CepNotFound()
            } catch (e: HttpResponseErrorException) {
                events.value = AlertEvent.CepNotFound()
            }
        }
    }
}