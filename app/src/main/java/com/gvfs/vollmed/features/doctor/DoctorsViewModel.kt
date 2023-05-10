package com.gvfs.vollmed.features.doctor

import androidx.lifecycle.ViewModel
import com.gvfs.vollmed.features.doctor.model.DoctorModel

class DoctorsViewModel : ViewModel() {
    // TODO: Implement the ViewModel


    fun getDoctors(): List<DoctorModel> {
        return listOf(
            DoctorModel(1, "José Silva"),
            DoctorModel(2, "André Santos"),
            DoctorModel(3, "Maria Oliveira"),
            DoctorModel(4, "Carlos Souza"),
            DoctorModel(5, "Ana Rodrigues"),
            DoctorModel(6, "Paulo Almeida"),
            DoctorModel(7, "Fernanda Costa"),
            DoctorModel(8, "Luiz Pereira"),
            DoctorModel(9, "Mariana Gomes"),
            DoctorModel(10, "Ricardo Fernandes"),
            DoctorModel(11, "Camila Cardoso"),
            DoctorModel(12, "Pedro Martins"),
            DoctorModel(13, "Sofia Barbosa"),
            DoctorModel(14, "Gustavo Lima")
        )
    }
}