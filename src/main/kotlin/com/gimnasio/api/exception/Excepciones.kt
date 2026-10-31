package com.gimnasio.api.exception

class RecursoNoEncontradoException(mensaje: String) : RuntimeException(mensaje)

class ConflictoException(mensaje: String) : RuntimeException(mensaje)

class DatoInvalidoException(val campo: String, mensaje: String) : RuntimeException(mensaje)