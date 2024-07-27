package com.optimiza.clickbarber.repository

import com.optimiza.clickbarber.model.usuario.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByIdExterno(idExterno: UUID): Optional<Usuario>
    fun findByEmail(email: String): Optional<Usuario>

}