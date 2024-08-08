package com.optimiza.clickbarber.model.formaspagamento.dto;

import com.optimiza.clickbarber.model.formaspagamento.FormaPagamento;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FormaPagamentoMapper {

    public FormaPagamentoRespostaDto toRespostaDto(FormaPagamento formaPagamento) {
        return new FormaPagamentoRespostaDto(
                Objects.requireNonNull(formaPagamento.getIdExterno()),
                formaPagamento.getTipo(),
                formaPagamento.getAtivo()
        );
    }

}
