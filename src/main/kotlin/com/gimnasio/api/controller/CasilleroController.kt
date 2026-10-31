package com.gimnasio.api.controller

import com.gimnasio.api.dto.ReservaRequest
import com.gimnasio.api.model.Reserva
import com.gimnasio.api.service.ReservaService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/casilleros")
class CasilleroController(private val reservaService: ReservaService) {

    @PostMapping("/{numero}/reservas")
    fun reservar(
        @PathVariable numero: Int,
        @Valid @RequestBody datos: ReservaRequest,
    ): ResponseEntity<Reserva> {
        val reserva = reservaService.reservar(numero, datos)
        return ResponseEntity.created(URI.create("/casilleros/$numero")).body(reserva)
    }
}