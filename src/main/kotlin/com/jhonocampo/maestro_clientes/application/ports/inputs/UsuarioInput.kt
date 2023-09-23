package com.jhonocampo.maestro_clientes.application.ports.inputs

import com.jhonocampo.maestro_clientes.application.dto.UsuarioDto

interface UsuarioInput {
    fun crearUsuario(usuario: UsuarioDto): UsuarioDto?
    fun actualizarUsuario(usuario: UsuarioDto): UsuarioDto?
    fun eliminarUsuario(id: String): UsuarioDto
    fun consultarUsuario(id: String): UsuarioDto?
    fun consultarUsuarios(): List<UsuarioDto>
}