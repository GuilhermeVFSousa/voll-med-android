package com.gvfs.vollmed.features.doctor.domain

import com.gvfs.vollmed.features.shareddomain.Endereco
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Medico")
data class DoctorCreate (
    val nome: String,
    val email: String,
    val telefone: String,
    val crm: String,
    val especialidade: String,
    val endereco: Endereco
    )