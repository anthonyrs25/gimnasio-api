package com.gimnasio.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "reserva")
class Reserva(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "numero_casillero", nullable = false, unique = true)
    var numeroCasillero: Int = 0,

    @Column(name = "socio_id", nullable = false, unique = true)
    var socioId: Long = 0,

    @Column(nullable = false)
    var meses: Int = 0,
)