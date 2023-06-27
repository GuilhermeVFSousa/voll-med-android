package com.gvfs.vollmed.features.alert

sealed class AlertEvent {
    data class CepNotFound(val message: String = "CEP não encontrado") : AlertEvent()
    data class PatientCreated(val message: String = "Paciente cadastrado com sucesso") : AlertEvent()
    data class PatientCreatedError(val message: String = "Erro ao cadastrar paciente") : AlertEvent()
}