package com.optimiza.clickbarber.controller

import com.optimiza.clickbarber.model.Resposta
import com.optimiza.clickbarber.model.RespostaUtils
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoAtualizarDto
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoCadastroDto
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoRespostaDto
import com.optimiza.clickbarber.service.FormaPagamentoService
import com.optimiza.clickbarber.utils.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/formas-pagamento")
class FormaPagamentoController(
    private val formaPagamentoService: FormaPagamentoService
) {

    @GetMapping("/barbearia/{idExternoBarbearia}")
    fun buscarPorIdExternoBarbearia(@PathVariable idExternoBarbearia: UUID): Resposta<List<FormaPagamentoRespostaDto>> {
        val formasDePagamento = formaPagamentoService.buscarPorIdExternoBarbearia(idExternoBarbearia = idExternoBarbearia)
        return RespostaUtils.ok(Constants.Success.FORMAS_PAGAMENTO_ENCONTRADAS, formasDePagamento)
    }

    @PostMapping
    fun cadastrar(@RequestBody formaPagamento: FormaPagamentoCadastroDto): Resposta<FormaPagamentoRespostaDto> {
        val formaDePagamentoCadastrado = formaPagamentoService.cadastrar(formaPagamento)
        return RespostaUtils.created(Constants.Success.FORMA_PAGAMENTO_CADASTRADA_COM_SUCESSO, formaDePagamentoCadastrado)
    }

    @PutMapping
    fun atualizar(@RequestBody formaPagamentoAtualizar: FormaPagamentoAtualizarDto): Resposta<FormaPagamentoRespostaDto> {
        val formaPagamentoAtualizada = formaPagamentoService.atualizar(formaPagamentoAtualizar = formaPagamentoAtualizar)
        return RespostaUtils.ok(Constants.Success.FORMA_PAGAMENTO_ATUALIZADA_COM_SUCESSO, formaPagamentoAtualizada)
    }

    @DeleteMapping("/{idExterno}")
    fun deletarPorIdExterno(@PathVariable idExterno: UUID): ResponseEntity<Void> {
        formaPagamentoService.deletarPorIdExterno(idExterno = idExterno)
        return ResponseEntity.noContent().build()
    }

}