package com.gvfs.vollmed.features.patient.domain

import com.gvfs.vollmed.features.shareddomain.Endereco
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Paciente")
data class PatientCreate (
    val nome: String,
    val email: String,
    val telefone: String,
    val cpf: String,
    val endereco: Endereco
    )