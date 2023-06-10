package com.gvfs.vollmed.features.patient.domain

import com.gvfs.vollmed.features.doctor.domain.DoctorResume
import com.gvfs.vollmed.features.shareddomain.pageable.Pageable
import com.gvfs.vollmed.features.shareddomain.pageable.Sort
import kotlinx.serialization.Serializable

@Serializable

data class PageablePatient (
    val content: List<PatientResume>,
    val pageable: Pageable,
    val totalPages: String,
    val totalElements: String,
    val last: Boolean,
    val size: String,
    val number: Int,
    val sort: Sort,
    val numberOfElements: Long,
    val first: Boolean,
    val empty: Boolean
    )