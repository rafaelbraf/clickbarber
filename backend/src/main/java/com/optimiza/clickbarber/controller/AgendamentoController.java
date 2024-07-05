package com.optimiza.clickbarber.controller;

import com.optimiza.clickbarber.model.Agendamento;
import com.optimiza.clickbarber.model.Resposta;
import com.optimiza.clickbarber.model.RespostaUtils;
import com.optimiza.clickbarber.model.dto.agendamento.AgendamentoAtualizarDto;
import com.optimiza.clickbarber.model.dto.agendamento.AgendamentoCadastroDto;
import com.optimiza.clickbarber.model.dto.agendamento.AgendamentoDto;
import com.optimiza.clickbarber.model.dto.agendamento.AgendamentoRespostaDto;
import com.optimiza.clickbarber.service.AgendamentoService;
import com.optimiza.clickbarber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @Autowired
    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping("/{id}")
    public Resposta<AgendamentoDto> buscarPorId(@PathVariable Long id) {
        var agendamento = agendamentoService.buscarPorId(id);
        return RespostaUtils.ok(Constants.Success.AGENDAMENTO_ENCONTRADO_COM_SUCESSO, agendamento);
    }

    @GetMapping("/barbearias/{idExternoBarbearia}")
    public Resposta<List<AgendamentoRespostaDto>> buscarPorIdExternoBarbearia(@PathVariable UUID idExternoBarbearia) {
        var agendamentos = agendamentoService.buscarPorIdExternoBarbearia(idExternoBarbearia);
        var mensagem = Constants.Success.AGENDAMENTOS_ENCONTRADOS + idExternoBarbearia;
        return RespostaUtils.ok(mensagem, agendamentos);
    }

    @GetMapping("/data-hora/{dataHora}/barbearias/{barbeariaId}")
    public Resposta<List<Agendamento>> buscarPorDataHoraEBarbeariaId(@PathVariable Integer barbeariaId, @PathVariable ZonedDateTime dataHora) {
        var agendamentos = agendamentoService.buscarPorBarbeariaIdEDataHora(barbeariaId, dataHora);
        return RespostaUtils.ok(Constants.Success.AGENDAMENTOS_ENCONTRADOS, agendamentos);
    }

    @PostMapping
    public ResponseEntity<Resposta<AgendamentoDto>> cadastrar(@RequestBody AgendamentoCadastroDto agendamentoCadastro) {
        var agendamentoCadastrado = agendamentoService.cadastrar(agendamentoCadastro);
        var resposta = RespostaUtils.created(Constants.Success.AGENDAMENTO_CADASTRADO_COM_SUCESSO, agendamentoCadastrado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping
    public Resposta<AgendamentoDto> atualizar(@RequestBody AgendamentoAtualizarDto agendamento) {
        var agendamentoAtualizado = agendamentoService.atualizar(agendamento);
        return RespostaUtils.ok(Constants.Success.AGENDAMENTO_ATUALIZADO_COM_SUCESSO, agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        agendamentoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
