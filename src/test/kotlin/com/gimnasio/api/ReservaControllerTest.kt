package com.gimnasio.api

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class ReservaControllerTest(
    @Autowired private val context: WebApplicationContext,
) {

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun preparar() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    fun `reservar un casillero libre responde 201 con Location`() {
        mockMvc.post("/casilleros/8/reservas") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"socioId":1,"meses":3}"""
        }.andExpect {
            status { isCreated() }
            header { string("Location", "/casilleros/8") }
        }
    }

    @Test
    fun `reservar un casillero inexistente responde 404`() {
        mockMvc.post("/casilleros/99/reservas") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"socioId":2,"meses":3}"""
        }.andExpect {
            status { isNotFound() }
            jsonPath("$.estado") { value(404) }
        }
    }
}