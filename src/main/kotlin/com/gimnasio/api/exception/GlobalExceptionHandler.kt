package com.gimnasio.api.exception

import com.gimnasio.api.dto.DetalleError
import com.gimnasio.api.dto.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.OffsetDateTime

@RestControllerAdvice
class GlobalExceptionHandler(
    @Value("\${gimnasio.validacion.estado}") private val estadoValidacion: Int,
) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validacion(ex: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val detalles = ex.bindingResult.fieldErrors
            .map { DetalleError(it.field, it.defaultMessage ?: "Valor no válido") }
            .sortedBy { it.campo }
        return responder(estadoValidacion, "Los datos enviados no son válidos", request, detalles)
    }

    @ExceptionHandler(DatoInvalidoException::class)
    fun datoInvalido(ex: DatoInvalidoException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val detalle = DetalleError(ex.campo, ex.message ?: "Valor no válido")
        return responder(estadoValidacion, "Los datos enviados no son válidos", request, listOf(detalle))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun jsonIlegible(request: HttpServletRequest): ResponseEntity<ErrorResponse> =
        responder(400, "El cuerpo de la petición no se pudo leer", request)

    @ExceptionHandler(RecursoNoEncontradoException::class)
    fun noEncontrado(ex: RecursoNoEncontradoException, request: HttpServletRequest): ResponseEntity<ErrorResponse> =
        responder(404, ex.message ?: "No encontrado", request)

    @ExceptionHandler(ConflictoException::class)
    fun conflicto(ex: ConflictoException, request: HttpServletRequest): ResponseEntity<ErrorResponse> =
        responder(409, ex.message ?: "Conflicto", request)

    private fun responder(
        estado: Int,
        mensaje: String,
        request: HttpServletRequest,
        detalles: List<DetalleError> = emptyList(),
    ): ResponseEntity<ErrorResponse> {
        val cuerpo = ErrorResponse(
            estado = estado,
            error = nombreDelError(estado),
            mensaje = mensaje,
            ruta = request.requestURI,
            momento = OffsetDateTime.now(),
            detalles = detalles,
        )
        return ResponseEntity.status(estado).body(cuerpo)
    }

    private fun nombreDelError(estado: Int): String = when (estado) {
        400 -> "Solicitud incorrecta"
        404 -> "No encontrado"
        409 -> "Conflicto"
        422 -> "Datos no válidos"
        else -> "Error"
    }
}