package com.jhonocampo.maestro_clientes.controllers

import com.jhonocampo.maestro_clientes.application.dto.UsuarioDto
import com.jhonocampo.maestro_clientes.infrastructure.persistence.repositories.UsuarioRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @BeforeEach
    fun setUp() {
        usuarioRepository.deleteAll()
    }

    @Test
    fun crearUsuario() {
        val usuarioDto = UsuarioDto(
            id = null,
            tipoIdentificacion = "CC",
            identificacion = "1112482910",
            nombre = "Jhon",
            genero = "M"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/usuario/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioDto.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        val usuarioGuardado = usuarioRepository.getUsuarioByIdentificacion(usuarioDto.identificacion)
        assertNotNull(usuarioGuardado)
        assertEquals(usuarioDto.tipoIdentificacion, usuarioGuardado!!.tipo_identificacion)
        assertEquals(usuarioDto.identificacion, usuarioGuardado.identificacion)
        assertEquals(usuarioDto.nombre, usuarioGuardado.nombre)
        assertEquals(usuarioDto.genero, usuarioGuardado.genero)
    }

    @Test
    fun actualizarUsuario() {
        val usuarioDto = UsuarioDto(
            id = null,
            tipoIdentificacion = "CC",
            identificacion = "1107510034",
            nombre = "Jhon",
            genero = "M"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/usuario/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioDto.toJsonString())
        )

        val usuarioDtoActualizado = UsuarioDto(
            id = "1",
            tipoIdentificacion = "CC",
            identificacion = "1107510034",
            nombre = "Marilyn",
            genero = "F"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/usuario/actualizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioDtoActualizado.toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        val usuarioActualizado = usuarioRepository.getUsuarioByIdentificacion(usuarioDtoActualizado.identificacion)
        assertNotNull(usuarioActualizado)
        assertEquals(usuarioDtoActualizado.tipoIdentificacion, usuarioActualizado?.tipo_identificacion)
        assertEquals(usuarioDtoActualizado.identificacion, usuarioActualizado?.identificacion)
        assertEquals(usuarioDtoActualizado.nombre, usuarioActualizado?.nombre)
        assertEquals(usuarioDtoActualizado.genero, usuarioActualizado?.genero)
    }

    @Test
    fun eliminarUsuario() {
        val usuarioDto = UsuarioDto(
            id = null,
            tipoIdentificacion = "CC",
            identificacion = "1107510034",
            nombre = "Jhon",
            genero = "M"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/usuario/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioDto.toJsonString())
        )
        val usuarioCreado = usuarioRepository.getUsuarioByIdentificacion(usuarioDto.identificacion)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/usuario/eliminar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UsuarioDto(
                    id = usuarioCreado!!.id,
                    tipoIdentificacion = "CC",
                    identificacion = "1107510034",
                    nombre = "Jhon",
                    genero = "M"
                ).toJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        val usuarioEliminado = usuarioRepository.getUsuarioByIdentificacion(usuarioCreado!!.identificacion)
        assertNull(usuarioEliminado)
    }

    @Test
    fun consultarUsuario() {
        val usuarioDto = UsuarioDto(
            id = null,
            tipoIdentificacion = "CC",
            identificacion = "1107510034",
            nombre = "Jhon",
            genero = "M"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/usuario/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioDto.toJsonString())
        )

        val usuarioCreado = usuarioRepository.getUsuarioByIdentificacion(usuarioDto.identificacion)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/usuario/consultar/${usuarioCreado!!.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(usuarioCreado.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.tipoIdentificacion").value(usuarioCreado.tipo_identificacion))
            .andExpect(MockMvcResultMatchers.jsonPath("$.identificacion").value(usuarioCreado.identificacion))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(usuarioCreado.nombre))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genero").value(usuarioCreado.genero))
    }

    @Test
    fun consultarUsuarios() {
        val usuarioDto = UsuarioDto(
            id = null,
            tipoIdentificacion = "CC",
            identificacion = "1107510034",
            nombre = "Jhon",
            genero = "M"
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/usuario/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioDto.toJsonString())
        )

        val usuarioCreado = usuarioRepository.getUsuarioByIdentificacion(usuarioDto.identificacion)
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/usuario/consultar-todos")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(usuarioCreado!!.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].tipoIdentificacion").value(usuarioCreado.tipo_identificacion))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].identificacion").value(usuarioCreado.identificacion))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre").value(usuarioCreado.nombre))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].genero").value(usuarioCreado.genero))
    }
}