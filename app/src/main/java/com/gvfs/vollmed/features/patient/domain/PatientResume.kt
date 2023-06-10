package com.gvfs.vollmed.features.patient.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Patient")
data class PatientResume (
    val id: Long,
    val nome: String,
    val email: String,
    val telefone: String,
    val cpf: String,
    )