package com.optimiza.clickbarber.repository;

import com.optimiza.clickbarber.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    @Query("SELECT s FROM Barbearia b INNER JOIN Servico s ON b.idExterno = :idExternoBarbearia")
    List<Servico> findByIdExternoBarbearia(@Param("idExternoBarbearia") UUID idExternoBarbearia);

}
