package com.optimiza.clickbarber.controller;

import com.optimiza.clickbarber.model.Resposta;
import com.optimiza.clickbarber.model.RespostaUtils;
import com.optimiza.clickbarber.model.Servico;
import com.optimiza.clickbarber.model.dto.servico.ServicoAtualizarDto;
import com.optimiza.clickbarber.model.dto.servico.ServicoDto;
import com.optimiza.clickbarber.service.ServicoService;
import com.optimiza.clickbarber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public Resposta<List<Servico>> buscarTodos() {
        var servicos = servicoService.buscarTodos();
        return RespostaUtils.ok(Constants.Success.SERVICOS_ENCONTRADOS, servicos);
    }

    @GetMapping("/{id}")
    public Resposta<Servico> buscarPorId(@PathVariable Long id) {
        var servicoEncontrado = servicoService.buscarPorId(id);
        return RespostaUtils.ok(Constants.Success.SERVICO_ENCONTRADO_COM_SUCESSO, servicoEncontrado);
    }

    @GetMapping("/barbearia/{barbeariaId}")
    public Resposta<List<Servico>> buscarPorBarbeariaId(@PathVariable Integer barbeariaId) {
        var servicosEncontrados = servicoService.buscarPorBarbeariaId(barbeariaId);
        return RespostaUtils.ok(Constants.Success.SERVICOS_ENCONTRADOS_DA_BARBEARIA + barbeariaId, servicosEncontrados);
    }

    @PostMapping
    public ResponseEntity<Resposta<Servico>> cadastrar(@RequestBody ServicoDto servico) {
        var servicoCadastrado = servicoService.cadastrar(servico);
        var resposta = RespostaUtils.created(Constants.Success.SERVICO_CADASTRADO_COM_SUCESSO, servicoCadastrado);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping
    public Resposta<Servico> atualizar(@RequestBody ServicoAtualizarDto servicoAtualizar) {
        var servicoAtualizado = servicoService.atualizar(servicoAtualizar);
        return RespostaUtils.ok(Constants.Success.SERVICO_ATUALIZADO_COM_SUCESSO, servicoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        servicoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
