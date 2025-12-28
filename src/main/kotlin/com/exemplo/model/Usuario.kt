package com.exemplo.model
import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id: Int,
    val nome: String,
    val email: String
)



