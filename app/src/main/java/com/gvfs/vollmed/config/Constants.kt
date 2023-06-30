package com.gvfs.vollmed.config

object Constants {
    const val BASE_URL = "https://voll-med-api-production.up.railway.app/"
//    const val BASE_URL = "http://localhost:8080/"
    const val VIA_CEP_URL = "https://viacep.com.br/ws/"

    const val LOGIN_ENDPOINT = "login"
    const val DOCTOR_ENDPOINT = "medicos?size=1000&page=0"
    const val PATIENT_ENDPOINT = "pacientes?size=1000&page=0"
    const val APPOINTMENT_ENDPOINT = "consultas"
    const val DOCTOR_SPECIALTY_ENDPOINT = "medicos/especialidades"

    const val SHARED_PREFERENCES_NAME = "VollMedAuthToken"
    const val TOKEN_KEY = "token"

    const val HTTP_REQUEST_TIMEOUT = 15000L
}