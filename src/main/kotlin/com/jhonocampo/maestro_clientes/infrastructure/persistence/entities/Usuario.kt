package com.jhonocampo.maestro_clientes.infrastructure.persistence.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Usuario(
    @Id
    val id: String?,
    val tipo_identificacion: String,
    val identificacion: String,
    var nombre: String,
    var genero: String
)
