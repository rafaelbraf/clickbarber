package com.optimiza.clickbarber.service

import com.optimiza.clickbarber.exception.ResourceNotFoundException
import com.optimiza.clickbarber.model.formaspagamento.FormaPagamento
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoAtualizarDto
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoCadastroDto
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoRespostaDto
import com.optimiza.clickbarber.model.formaspagamento.toRespostaDto
import com.optimiza.clickbarber.repository.FormaPagamentoRepository
import com.optimiza.clickbarber.utils.Constants
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FormaPagamentoService(
    private val formaPagamentoRepository: FormaPagamentoRepository,
    private val barbeariaService: BarbeariaService
) {

    fun buscarPorIdExternoBarbearia(idExternoBarbearia: UUID): List<FormaPagamentoRespostaDto> {
        val barbearia = barbeariaService.buscarPorIdExterno(idExternoBarbearia)

        return formaPagamentoRepository.findByBarbeariaId(barbeariaId = barbearia.id).map {
            it.toRespostaDto()
        }
    }

    fun cadastrar(formaPagamentoCadastroDto: FormaPagamentoCadastroDto): FormaPagamentoRespostaDto {
        val barbearia = barbeariaService.buscarPorIdExterno(formaPagamentoCadastroDto.idExternoBarbearia)
        val formaPagamento = FormaPagamento(
            tipo = formaPagamentoCadastroDto.tipo,
            barbearia = barbearia,
            ativo = formaPagamentoCadastroDto.ativo
        )

        return formaPagamentoRepository.save(formaPagamento).toRespostaDto()
    }

    fun atualizar(formaPagamentoAtualizar: FormaPagamentoAtualizarDto): FormaPagamentoRespostaDto {
        val formaPagamento = formaPagamentoRepository.findByIdExterno(formaPagamentoAtualizar.idExterno)
            .orElseThrow { ResourceNotFoundException(Constants.Entity.FORMA_PAGAMENTO, Constants.Attribute.ID_EXTERNO, formaPagamentoAtualizar.idExterno.toString()) }

        val formaPagamentoParaAtualizar = FormaPagamento(
            id = formaPagamento.id,
            tipo = formaPagamentoAtualizar.tipo,
            ativo = formaPagamentoAtualizar.ativo,
            barbearia = formaPagamento.barbearia,
            idExterno = formaPagamento.idExterno
        )

        val formaPagamentoAtualizado = formaPagamentoRepository.save(formaPagamentoParaAtualizar)
        return formaPagamentoAtualizado.toRespostaDto()
    }

    fun deletarPorIdExterno(idExterno: UUID) {
        formaPagamentoRepository.findByIdExterno(idExterno = idExterno)
            .orElseThrow { ResourceNotFoundException(Constants.Entity.FORMA_PAGAMENTO, Constants.Attribute.ID_EXTERNO, idExterno.toString()) }
            .apply {
                if (this.id != null) {
                    formaPagamentoRepository.deleteById(this.id)
                }
            }
    }

}