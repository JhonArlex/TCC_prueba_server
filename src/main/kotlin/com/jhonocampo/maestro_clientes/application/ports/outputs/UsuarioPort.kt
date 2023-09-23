package com.jhonocampo.maestro_clientes.application.ports.outputs

import com.jhonocampo.maestro_clientes.application.dto.UsuarioDto

interface UsuarioPort {
    fun crearUsuario(usuario: UsuarioDto): UsuarioDto?
    fun actualizarUsuario(usuario: UsuarioDto): UsuarioDto?
    fun eliminarUsuario(id: String): UsuarioDto
    fun consultarUsuario(id: String): UsuarioDto?
    fun consultarUsuarios(): List<UsuarioDto>
}