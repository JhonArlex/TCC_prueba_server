package com.jhonocampo.maestro_clientes.adapters

import com.jhonocampo.maestro_clientes.application.dto.UsuarioDto
import com.jhonocampo.maestro_clientes.application.ports.outputs.UsuarioPort
import com.jhonocampo.maestro_clientes.infrastructure.persistence.entities.Usuario
import com.jhonocampo.maestro_clientes.infrastructure.persistence.repositories.UsuarioRepository
import org.springframework.stereotype.Component

@Component
class UsuarioAdapter(val usuarioRepository: UsuarioRepository): UsuarioPort {
    override fun crearUsuario(usuario: UsuarioDto): UsuarioDto? {
        val usuarioEntity = Usuario(
            null,
            usuario.tipoIdentificacion,
            usuario.identificacion,
            usuario.nombre,
            usuario.genero
        )
        val usuarioCreado = usuarioRepository.crearUsuario(usuarioEntity)
        return if (usuarioCreado != null) {
            UsuarioDto(
                usuarioCreado.id,
                usuarioCreado.tipo_identificacion,
                usuarioCreado.identificacion,
                usuarioCreado.nombre,
                usuarioCreado.genero
            )
        } else {
            null
        }
    }

    override fun actualizarUsuario(usuario: UsuarioDto): UsuarioDto {
        val usuarioEntity = Usuario(
            usuario.id,
            usuario.tipoIdentificacion,
            usuario.identificacion,
            usuario.nombre,
            usuario.genero
        )
        val usuarioActualizado = usuarioRepository.updateUsuarioById(usuarioEntity)
        return UsuarioDto(
            usuarioActualizado.id,
            usuarioActualizado.tipo_identificacion,
            usuarioActualizado.identificacion,
            usuarioActualizado.nombre,
            usuarioActualizado.genero
        )
    }

    override fun eliminarUsuario(id: String): UsuarioDto {
        val usuarioEntity = usuarioRepository.deleteUsuario(id)
        if (usuarioEntity != null) {
            return UsuarioDto(
                usuarioEntity.id,
                usuarioEntity.tipo_identificacion,
                usuarioEntity.identificacion,
                usuarioEntity.nombre,
                usuarioEntity.genero
            )
        }
        return UsuarioDto(null, "", "", "", "")
    }

    override fun consultarUsuario(id: String): UsuarioDto? {
        val usuarioEntity = usuarioRepository.getUsuarioById(id)
        if (usuarioEntity != null) {
            return UsuarioDto(
                usuarioEntity.id,
                usuarioEntity.tipo_identificacion,
                usuarioEntity.identificacion,
                usuarioEntity.nombre,
                usuarioEntity.genero
            )
        } else {
            return null
        }
    }

    override fun consultarUsuarios(): List<UsuarioDto> {
        val usuariosEntity = usuarioRepository.getUsuarios()
        return usuariosEntity.map {
            UsuarioDto(
                it.id,
                it.tipo_identificacion,
                it.identificacion,
                it.nombre,
                it.genero
            )
        }
    }

}