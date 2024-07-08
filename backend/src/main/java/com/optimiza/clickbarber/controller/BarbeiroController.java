package com.optimiza.clickbarber.controller;

import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import com.optimiza.clickbarber.model.Resposta;
import com.optimiza.clickbarber.model.RespostaUtils;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroAtualizarDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroCadastroDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroDto;
import com.optimiza.clickbarber.service.BarbeiroService;
import com.optimiza.clickbarber.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/barbeiros")
public class BarbeiroController {

    private final BarbeiroService barbeiroService;

    public BarbeiroController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    @GetMapping("/{id}")
    public Resposta<Barbeiro> buscarPorId(@PathVariable Long id) {
        var barbeiro = barbeiroService.buscarPorId(id);
        return RespostaUtils.ok(Constants.Success.BARBEIRO_ENCONTRADO_COM_SUCESSO, barbeiro);
    }

    @GetMapping("/barbearia/{idExternoBarbearia}")
    public Resposta<List<BarbeiroDto>> buscarPorBarbeariaId(@PathVariable UUID idExternoBarbearia) {
        var barbeiros = barbeiroService.buscarPorIdExternoBarbearia(idExternoBarbearia);
        return RespostaUtils.ok(Constants.Success.BARBEIROS_ENCONTRADOS_DA_BARBEARIA + idExternoBarbearia, barbeiros);
    }

    @PostMapping
    public ResponseEntity<Resposta<BarbeiroDto>> cadastrar(@RequestBody BarbeiroCadastroDto barbeiroCadastro) {
        var barbeiroCadastrado = barbeiroService.cadastrar(barbeiroCadastro);
        var resposta = RespostaUtils.created(Constants.Success.BARBEIRO_CADASTRADO_COM_SUCESSO, barbeiroCadastrado);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping
    public Resposta<Barbeiro> atualizar(@RequestBody BarbeiroAtualizarDto barbeiroAtualizar) {
        var barbeiroAtualizado = barbeiroService.atualizar(barbeiroAtualizar);
        return RespostaUtils.ok(Constants.Success.BARBEIRO_ATUALIZADO_COM_SUCESSO, barbeiroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        barbeiroService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

}
