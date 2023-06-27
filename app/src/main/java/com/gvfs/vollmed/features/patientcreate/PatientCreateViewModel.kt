package com.gvfs.vollmed.features.patientcreate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvfs.vollmed.exceptions.CepNotFoundException
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.alert.AlertEvent
import com.gvfs.vollmed.features.patient.PatientService
import com.gvfs.vollmed.features.patient.domain.PatientCreate
import com.gvfs.vollmed.features.shareddomain.Endereco
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientCreateViewModel  @Inject constructor(
    private val service: PatientService) : ViewModel() {

    private val _address = MutableLiveData<Endereco>()
    val address: LiveData<Endereco>get() = _address
    val events: MutableLiveData<AlertEvent> = MutableLiveData()
    val createEvent: MutableLiveData<AlertEvent> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.O)
    fun create(patientCreate: PatientCreate) {
        viewModelScope.launch {
            val response = service.createPatient(patientCreate)
            if (response) createEvent.value = AlertEvent.PatientCreated()
            else createEvent.value = AlertEvent.PatientCreatedError()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCep(cep: String) {
        viewModelScope.launch {
            try {
                _address.value = service.getCEP(cep)
                if(_address.value?.cep == "") events.value = AlertEvent.CepNotFound()
            } catch (e: HttpResponseErrorException) {
                events.value = AlertEvent.CepNotFound()
            }
        }
    }
}