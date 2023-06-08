package com.gvfs.vollmed.features.login.domain

import kotlinx.serialization.Serializable

@Serializable
data class Login (
    var login: String,
    var senha: String
    )