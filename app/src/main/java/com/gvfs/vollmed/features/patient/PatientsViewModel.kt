package com.gvfs.vollmed.features.patient

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvfs.vollmed.exceptions.CepNotFoundException
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.patient.domain.PatientResume
import com.gvfs.vollmed.features.shareddomain.Endereco
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor(
    private val service: PatientService
) : ViewModel() {

    private val _patients = MutableLiveData<List<PatientResume>>()
    val patients: LiveData<List<PatientResume>>get() = _patients

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPatients() {
        viewModelScope.launch {
            try {
                _patients.value = service.getPatients()
            } catch (e: HttpResponseErrorException) {
                emptyList<PatientResume>()
                return@launch
            }
        }
    }

}