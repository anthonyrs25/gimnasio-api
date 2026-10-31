package com.gimnasio.api.service

import com.gimnasio.api.dto.ReservaRequest
import com.gimnasio.api.exception.ConflictoException
import com.gimnasio.api.exception.DatoInvalidoException
import com.gimnasio.api.exception.RecursoNoEncontradoException
import com.gimnasio.api.model.Reserva
import com.gimnasio.api.repository.ReservaRepository
import com.gimnasio.api.repository.SocioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservaService(
    private val reservaRepository: ReservaRepository,
    private val socioRepository: SocioRepository,
) {

    @Transactional
    fun reservar(numero: Int, datos: ReservaRequest): Reserva {
        if (numero !in 1..30) {
            throw RecursoNoEncontradoException("El casillero $numero no existe")
        }

        val socioId = datos.socioId!!
        if (!socioRepository.existsById(socioId)) {
            throw DatoInvalidoException("socioId", "No existe un socio con id $socioId")
        }

        if (reservaRepository.existsByNumeroCasillero(numero)) {
            throw ConflictoException("El casillero $numero ya está reservado")
        }

        if (reservaRepository.existsBySocioId(socioId)) {
            throw ConflictoException("El socio $socioId ya tiene un casillero reservado")
        }

        val reserva = Reserva(numeroCasillero = numero, socioId = socioId, meses = datos.meses!!)
        return reservaRepository.save(reserva)
    }
}