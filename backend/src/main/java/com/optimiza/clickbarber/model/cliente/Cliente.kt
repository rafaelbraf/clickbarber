package com.optimiza.clickbarber.model.cliente

import com.optimiza.clickbarber.model.barbearia.Barbearia
import com.optimiza.clickbarber.model.usuario.Usuario
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.*

@Table(name = "clientes")
@Entity
class Cliente(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    var idExterno: UUID?,
    val nome: String,
    val dataNascimento: LocalDate,
    val celular: String,

    @OneToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario?,

    @ManyToMany
    @JoinTable(
        name = "clientes_barbearias",
        joinColumns = [JoinColumn(name = "cliente_id")],
        inverseJoinColumns = [(JoinColumn(name = "barbearia_id"))]
    )
    val barbearias: MutableSet<Barbearia> = emptySet<Barbearia>() as MutableSet<Barbearia>
) {
    constructor() : this(
        id = null,
        idExterno = null,
        nome = "",
        dataNascimento = LocalDate.now(),
        celular = "",
        usuario = null,
        barbearias = mutableSetOf()
    )

    constructor(id: Long?, idExterno: UUID?, nome: String, dataNascimento: LocalDate, celular: String, barbearias: MutableSet<Barbearia>): this (
        id = id,
        idExterno = idExterno,
        nome = nome,
        dataNascimento = dataNascimento,
        celular = celular,
        usuario = null,
        barbearias = barbearias
    )

    constructor(id: Long?, nome: String, dataNascimento: LocalDate, celular: String, barbearias: MutableSet<Barbearia>): this (
        id = id,
        idExterno = null,
        nome = nome,
        dataNascimento = dataNascimento,
        celular = celular,
        barbearias = barbearias
    )

    constructor(nome: String, dataNascimento: LocalDate, celular: String, usuario: Usuario, barbearias: MutableSet<Barbearia>): this(
        id = null,
        idExterno = null,
        nome = nome,
        dataNascimento = dataNascimento,
        celular = celular,
        usuario = usuario,
        barbearias = barbearias
    )

    constructor(nome: String, dataNascimento: LocalDate, celular: String, usuario: Usuario): this(
        id = null,
        idExterno = null,
        nome = nome,
        dataNascimento = dataNascimento,
        celular = celular,
        usuario = usuario,
        barbearias = mutableSetOf()
    )

    @PrePersist
    fun gerarIdExterno() {
        if (idExterno == null) {
            idExterno = UUID.randomUUID()
        }
    }

}