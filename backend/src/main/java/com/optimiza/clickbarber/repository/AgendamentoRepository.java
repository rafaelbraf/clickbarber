package com.optimiza.clickbarber.repository;

import com.optimiza.clickbarber.model.agendamento.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    @Query("SELECT a FROM Agendamento a INNER JOIN Barbearia b ON a.barbearia.id = b.id WHERE b.idExterno = :idExternoBarbearia")
    List<Agendamento> findByIdExternoBarbearia(UUID idExternoBarbearia);

    List<Agendamento> findByBarbeariaId(Integer barbeariaId);

    List<Agendamento> findByDataHoraAndBarbeariaId(ZonedDateTime dataHora, Integer barbeariaId);

}
