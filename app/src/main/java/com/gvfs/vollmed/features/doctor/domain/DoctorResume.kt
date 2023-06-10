package com.gvfs.vollmed.features.doctor.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Doctor")
data class DoctorResume (
    val id: Long,
    val nome: String,
    val email: String,
    val telefone: String,
    val crm: String,
    val especialidade: String,
    )