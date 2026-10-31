package com.gimnasio.api.repository

import com.gimnasio.api.model.Socio
import org.springframework.data.jpa.repository.JpaRepository

interface SocioRepository : JpaRepository<Socio, Long>