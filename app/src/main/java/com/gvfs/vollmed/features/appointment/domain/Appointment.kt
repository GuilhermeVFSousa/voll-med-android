package com.gvfs.vollmed.features.appointment.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import com.gvfs.vollmed.extension.getBrazilianFormatString
import com.gvfs.vollmed.features.doctor.domain.DoctorResumeCompact
import com.gvfs.vollmed.features.patient.domain.PatientResumeCompact
import com.gvfs.vollmed.utils.serializer.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
@SerialName("Appointment")
data class Appointment (
    val id: Long,
    val medico: DoctorResumeCompact,
    val paciente: PatientResumeCompact,
    @Serializable(with = LocalDateTimeSerializer::class)
    val data: LocalDateTime,
    @SerialName("data_termino")
    @Serializable(with = LocalDateTimeSerializer::class)
    val dataTermino: LocalDateTime?
    ) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDate(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
        return data.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHourDate(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return data.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayMonthYearDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return data.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDurationInMinutes(): String {
        if (dataTermino != null) {
            val duration = Duration.between(data, dataTermino)
            return "${duration.toMinutes()} minutos"
        }
        return "Não informado"
    }
}