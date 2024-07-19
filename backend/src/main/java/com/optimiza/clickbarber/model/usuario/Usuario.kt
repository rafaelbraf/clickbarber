package com.optimiza.clickbarber.model.usuario

import com.optimiza.clickbarber.model.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import java.util.*

@Table(name = "usuarios")
@Entity
class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "id_externo", unique = true, nullable = false)
    var idExterno: UUID?,

    val email: String,
    var senha: String,

    @Enumerated(EnumType.STRING)
    val role: Role

) {
    constructor() : this(null, null, "", "", Role.DEFAULT)

    @PrePersist
    fun gerarIdExterno() {
        if (idExterno == null) {
            idExterno = UUID.randomUUID()
        }
    }
}