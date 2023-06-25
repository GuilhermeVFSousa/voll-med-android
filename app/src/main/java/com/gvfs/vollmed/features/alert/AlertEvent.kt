package com.gvfs.vollmed.features.alert

sealed class AlertEvent {
    data class CepNotFound(val message: String = "CEP não encontrado") : AlertEvent()
}