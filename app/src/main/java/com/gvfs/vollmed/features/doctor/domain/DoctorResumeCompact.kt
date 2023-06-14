package com.gvfs.vollmed.features.doctor.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Doctor")
data class DoctorResumeCompact (
    val nome: String,
    val crm: String,
    val especialidade: String
    )