package com.gvfs.vollmed.features.shareddomain

import kotlinx.serialization.Serializable

@Serializable
data class Endereco (
    var logradouro: String,
    var bairro: String,
    var cep: String,
    val numero: String,
    val complemento: String,
    var cidade: String,
    var uf: String
    )