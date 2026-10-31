package com.gimnasio.api.dto

import java.time.OffsetDateTime

data class DetalleError(
    val campo: String,
    val mensaje: String,
)

data class ErrorResponse(
    val estado: Int,
    val error: String,
    val mensaje: String,
    val ruta: String,
    val momento: OffsetDateTime,
    val detalles: List<DetalleError> = emptyList(),
)