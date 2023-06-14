package com.gvfs.vollmed.features.patient.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Patient")
data class PatientResumeCompact (
    val nome: String,
    val cpf: String
    )