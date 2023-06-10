package com.gvfs.vollmed.features.doctor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvfs.vollmed.exceptions.HttpResponseErrorException
import com.gvfs.vollmed.features.doctor.domain.DoctorResume
import com.gvfs.vollmed.features.doctor.domain.PageableDoctor
import com.gvfs.vollmed.features.doctor.model.DoctorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.utils.io.printStack
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsViewModel @Inject constructor(
    private val service: DoctorService
) : ViewModel() {
    private val _doctor = MutableLiveData<List<DoctorResume>>()
    val doctor: LiveData<List<DoctorResume>>get() = _doctor

    fun getDoctors() {
        viewModelScope.launch {
            try {
                _doctor.value = service.getDoctors()
            } catch (e: HttpResponseErrorException) {
                e.printStack()
                emptyList<DoctorResume>()
                return@launch
            }
        }
    }


//    fun getDoctors(): List<DoctorModel> {
//        return listOf(
//            DoctorModel(1, "José Silva"),
//            DoctorModel(2, "André Santos"),
//            DoctorModel(3, "Maria Oliveira"),
//            DoctorModel(4, "Carlos Souza"),
//            DoctorModel(5, "Ana Rodrigues"),
//            DoctorModel(6, "Paulo Almeida"),
//            DoctorModel(7, "Fernanda Costa"),
//            DoctorModel(8, "Luiz Pereira"),
//            DoctorModel(9, "Mariana Gomes"),
//            DoctorModel(10, "Ricardo Fernandes"),
//            DoctorModel(11, "Camila Cardoso"),
//            DoctorModel(12, "Pedro Martins"),
//            DoctorModel(13, "Sofia Barbosa"),
//            DoctorModel(14, "Gustavo Lima")
//        )
//    }
}