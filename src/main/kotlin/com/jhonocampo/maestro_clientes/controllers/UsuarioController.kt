package com.jhonocampo.maestro_clientes.controllers

import com.jhonocampo.maestro_clientes.application.dto.UsuarioDto
import com.jhonocampo.maestro_clientes.application.ports.inputs.UsuarioInput
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin(origins = ["*"])
class UsuarioController(
    private val usuarioInput: UsuarioInput
) {

    @PostMapping("/crear")
    fun crearUsuario(@RequestBody body: UsuarioDto): UsuarioDto? {
        return usuarioInput.crearUsuario(body)
    }

    @PutMapping("/actualizar")
    fun actualizarUsuario(@RequestBody body: UsuarioDto): UsuarioDto? {
        return usuarioInput.actualizarUsuario(body)
    }

    @DeleteMapping("/eliminar/{id}")
    fun eliminarUsuario(@PathVariable id: String): UsuarioDto? {
        return usuarioInput.eliminarUsuario(id)
    }

    @GetMapping("/consultar/{id}")
    fun consultarUsuario(@PathVariable id: String): UsuarioDto? {
        return usuarioInput.consultarUsuario(id)
    }

    @GetMapping("/consultar-todos")
    fun consultarUsuarios(): List<UsuarioDto> {
        return usuarioInput.consultarUsuarios()
    }

}