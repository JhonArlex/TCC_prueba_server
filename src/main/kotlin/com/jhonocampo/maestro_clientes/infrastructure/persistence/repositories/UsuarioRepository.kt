package com.jhonocampo.maestro_clientes.infrastructure.persistence.repositories

import com.jhonocampo.maestro_clientes.application.dto.UsuarioDto
import com.jhonocampo.maestro_clientes.infrastructure.persistence.entities.Usuario
import jakarta.persistence.EntityManager
import jakarta.persistence.ParameterMode
import org.springframework.stereotype.Repository
import java.util.logging.Logger

@Repository
class UsuarioRepository(private val entityManager: EntityManager) {

    private val logger: Logger = Logger.getLogger(UsuarioRepository::class.java.name)

    fun crearUsuario(usuario: Usuario): Usuario? {
        val query = entityManager.createStoredProcedureQuery("dbo.create_usuario")
        query.registerStoredProcedureParameter("tipo_identificacion", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("identificacion", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nombre", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("genero", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nuevoId", String::class.java, ParameterMode.OUT)

        query.setParameter("tipo_identificacion", usuario.tipo_identificacion)
        query.setParameter("identificacion", usuario.identificacion)
        query.setParameter("nombre", usuario.nombre)
        query.setParameter("genero", usuario.genero)

        query.execute()

        val id = query.getOutputParameterValue("nuevoId") as String?

        logger.info("Nuevo ID: ${id}")

        return id?.let {
            Usuario(
                id,
                usuario.tipo_identificacion,
                usuario.identificacion,
                usuario.nombre,
                usuario.genero
            )
        }
    }

    fun updateUsuario(usuario: Usuario): Usuario {
        val query = entityManager.createStoredProcedureQuery("dbo.update_usuario_by_identificacion")
        query.registerStoredProcedureParameter("identificacion", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nuevoNombre", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nuevoGenero", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nombre", String::class.java, ParameterMode.OUT)
        query.registerStoredProcedureParameter("genero", String::class.java, ParameterMode.OUT)

        query.setParameter("identificacion", usuario.identificacion)
        query.setParameter("nuevoNombre", usuario.nombre)
        query.setParameter("nuevoGenero", usuario.genero)

        query.execute()

        val nombre = query.getOutputParameterValue("nombre") as String?
        val genero = query.getOutputParameterValue("genero") as String?

        usuario.nombre = nombre ?: usuario.nombre
        usuario.genero = genero ?: usuario.genero

        return usuario
    }

    fun updateUsuarioById(usuario: Usuario): Usuario {
        val query = entityManager.createStoredProcedureQuery("dbo.update_usuario_by_id")
        query.registerStoredProcedureParameter("id", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nuevoTipoIdentificacion", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nuevoIdentificacion", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nuevoNombre", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("nuevoGenero", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("tipoIdentificacion", String::class.java, ParameterMode.OUT)
        query.registerStoredProcedureParameter("identificacion", String::class.java, ParameterMode.OUT)
        query.registerStoredProcedureParameter("nombre", String::class.java, ParameterMode.OUT)
        query.registerStoredProcedureParameter("genero", String::class.java, ParameterMode.OUT)

        query.setParameter("id", usuario.id)
        query.setParameter("nuevoTipoIdentificacion", usuario.tipo_identificacion)
        query.setParameter("nuevoIdentificacion", usuario.identificacion)
        query.setParameter("nuevoNombre", usuario.nombre)
        query.setParameter("nuevoGenero", usuario.genero)

        query.execute()

        val tipoIdentificacion = query.getOutputParameterValue("tipoIdentificacion") as String?
        val identificacion = query.getOutputParameterValue("identificacion") as String?
        val nombre = query.getOutputParameterValue("nombre") as String?
        val genero = query.getOutputParameterValue("genero") as String?

        return Usuario(
            usuario.id,
            tipoIdentificacion ?: usuario.tipo_identificacion,
            identificacion ?: usuario.identificacion,
            nombre ?: usuario.nombre,
            genero ?: usuario.genero
        )
    }

    fun deleteUsuario(id: String): Usuario {
        val query = entityManager.createStoredProcedureQuery("dbo.delete_by_id")
        query.registerStoredProcedureParameter("id", String::class.java, ParameterMode.IN)
        query.registerStoredProcedureParameter("tipoIdentificacion", String::class.java, ParameterMode.OUT)
        query.registerStoredProcedureParameter("identificacion", String::class.java, ParameterMode.OUT)
        query.registerStoredProcedureParameter("nombre", String::class.java, ParameterMode.OUT)
        query.registerStoredProcedureParameter("genero", String::class.java, ParameterMode.OUT)

        query.setParameter("id", id)

        query.execute()

        val tipoIdentificacion = query.getOutputParameterValue("tipoIdentificacion") as String?
        val identificacion = query.getOutputParameterValue("identificacion") as String?
        val nombre = query.getOutputParameterValue("nombre") as String?
        val genero = query.getOutputParameterValue("genero") as String?

        return Usuario(
            id,
            tipoIdentificacion ?: "",
            identificacion ?: "",
            nombre ?: "",
            genero ?: ""
        )
    }

    fun deleteAll() {
        val query = entityManager.createNativeQuery("DELETE FROM dbo.Usuario")
        query.executeUpdate()
    }

    fun getUsuarioById(id: String): Usuario? {
        val query = entityManager.createNativeQuery("SELECT * FROM dbo.get_by_id_usuario(:id)", Usuario::class.java)
        query.setParameter("id", id)
        return query.singleResult as Usuario?
    }

    fun getUsuarioByIdentificacion(identificacion: String): Usuario? {
        val query = entityManager.createNativeQuery("SELECT  * FROM dbo.get_by_identificacion_usuario(:identificacion)", Usuario::class.java)
        query.setParameter("identificacion", identificacion)
        val result = query.resultList.firstOrNull()
        return result as Usuario?
    }

    fun getUsuarios(): List<Usuario> {
        val query = entityManager.createNativeQuery("SELECT * FROM dbo.get_all_usuarios()", Usuario::class.java)
        return query.resultList as List<Usuario>
    }

}