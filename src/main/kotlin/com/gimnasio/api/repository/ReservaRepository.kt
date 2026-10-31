package com.gimnasio.api.repository

import com.gimnasio.api.model.Reserva
import org.springframework.data.jpa.repository.JpaRepository

interface ReservaRepository : JpaRepository<Reserva, Long> {
    fun existsByNumeroCasillero(numeroCasillero: Int): Boolean
    fun existsBySocioId(socioId: Long): Boolean
}