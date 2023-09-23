package com.jhonocampo.maestro_clientes.application.dto

data class UsuarioDto(
    val id: String?,
    val tipoIdentificacion: String,
    val identificacion: String,
    val nombre: String,
    val genero: String
) {
    fun toJsonString(): String {
        return "{\"id\":$id,\"tipoIdentificacion\":\"$tipoIdentificacion\",\"identificacion\":\"$identificacion\",\"nombre\":\"$nombre\",\"genero\":\"$genero\"}"
    }
}
