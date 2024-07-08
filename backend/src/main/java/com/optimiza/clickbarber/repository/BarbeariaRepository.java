package com.optimiza.clickbarber.repository;

import com.optimiza.clickbarber.model.barbearia.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {

    Optional<Barbearia> findByIdExterno(UUID idExterno);

    Optional<Barbearia> findByUsuarioId(Long usuarioId);

    List<Barbearia> findByNomeContainingIgnoreCase(String nome);
}