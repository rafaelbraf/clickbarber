package com.optimiza.clickbarber.repository;

import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {
    @Query("SELECT b1 FROM Barbeiro b1 INNER JOIN Barbearia b2 ON b2.idExterno = :idExternoBarbearia")
    List<Barbeiro> findByIdExternoBarbearia(UUID idExternoBarbearia);

    List<Barbeiro> findByBarbeariaId(Integer id);

    Optional<Barbeiro> findByUsuarioId(Long usuarioId);

}
