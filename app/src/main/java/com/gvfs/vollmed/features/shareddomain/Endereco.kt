package com.gvfs.vollmed.features.shareddomain

import kotlinx.serialization.Serializable

@Serializable
data class Endereco (
    val logradouro: String,
    val bairro: String,
    val cep: String,
    val numero: String,
    val complemento: String,
    val cidade: String,
    val uf: String
    )