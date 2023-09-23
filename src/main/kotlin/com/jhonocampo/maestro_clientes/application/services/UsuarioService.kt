package com.jhonocampo.maestro_clientes.application.services

import com.jhonocampo.maestro_clientes.application.dto.UsuarioDto
import com.jhonocampo.maestro_clientes.application.ports.inputs.UsuarioInput
import com.jhonocampo.maestro_clientes.application.ports.outputs.UsuarioPort
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val usuarioPort: UsuarioPort) : UsuarioInput {
    override fun crearUsuario(usuario: UsuarioDto): UsuarioDto? {
        return usuarioPort.crearUsuario(usuario)
    }

    override fun actualizarUsuario(usuario: UsuarioDto): UsuarioDto? {
        return usuarioPort.actualizarUsuario(usuario)
    }

    override fun eliminarUsuario(id: String): UsuarioDto {
        return usuarioPort.eliminarUsuario(id)
    }

    override fun consultarUsuario(id: String): UsuarioDto? {
        return usuarioPort.consultarUsuario(id)
    }

    override fun consultarUsuarios(): List<UsuarioDto> {
        return usuarioPort.consultarUsuarios()
    }
}