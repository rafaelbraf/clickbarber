package com.optimiza.clickbarber.repository;

import com.optimiza.clickbarber.model.barbearia.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {
    Optional<Barbearia> findByIdExterno(UUID idExterno);

    Optional<Barbearia> findByUsuarioId(Long usuarioId);

    List<Barbearia> findByNomeContainingIgnoreCase(String nome);

    @Modifying
    @Transactional
    @Query("UPDATE Barbearia b SET b.logoUrl = :logoUrl WHERE b.id = :id")
    void updateLogoUrlById(Long id, String logoUrl);
}