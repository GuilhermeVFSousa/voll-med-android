package com.gvfs.vollmed.config

object Constants {
    const val BASE_URL = "https://voll-med-api-production.up.railway.app/"
//    const val BASE_URL = "http://localhost:8080/"

    const val LOGIN_ENDPOINT = "login"
    const val DOCTOR_ENDPOINT = "medicos?size=1000&page=0"

    const val SHARED_PREFERENCES_NAME = "VollMedAuthToken"
    const val TOKEN_KEY = "token"

    const val HTTP_REQUEST_TIMEOUT = 15000L
}