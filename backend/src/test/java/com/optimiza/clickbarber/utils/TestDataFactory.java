package com.optimiza.clickbarber.utils;

import com.optimiza.clickbarber.model.Role;
import com.optimiza.clickbarber.model.agendamento.Agendamento;
import com.optimiza.clickbarber.model.agendamento.dto.*;
import com.optimiza.clickbarber.model.autenticacao.RespostaLogin;
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto;
import com.optimiza.clickbarber.model.barbearia.Barbearia;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaCadastroDto;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaDto;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaRespostaDto;
import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import com.optimiza.clickbarber.model.barbeiro.dto.*;
import com.optimiza.clickbarber.model.cliente.Cliente;
import com.optimiza.clickbarber.model.cliente.dto.ClienteCadastroDto;
import com.optimiza.clickbarber.model.cliente.dto.ClienteDto;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.servico.dto.ServicoAtualizarDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoCadastroDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoDto;
import com.optimiza.clickbarber.model.usuario.Usuario;
import com.optimiza.clickbarber.model.usuario.dto.UsuarioCadastrarDto;
import kotlin.jvm.internal.markers.KMutableSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestDataFactory {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static final String EMAIL_USUARIO = "teste@mail.com";
    private static final String SENHA_USUARIO = "teste";

    public static BarbeariaDto montarBarbeariaDto(UUID idExterno) {
        return BarbeariaDto.builder()
                .idExterno(idExterno)
                .nome("Barbearia Teste")
                .cnpj("0123456789101112")
                .endereco("Rua Teste, 123")
                .telefone("988888888")
                .build();
    }

    public static BarbeariaDto montarBarbeariaDto(UUID idExterno, String nome) {
        return BarbeariaDto.builder()
                .idExterno(idExterno)
                .nome(nome)
                .cnpj("0123456789101112")
                .endereco("Rua Teste, 123")
                .telefone("988888888")
                .build();
    }

    public static Barbearia montarBarbearia() {
        return Barbearia.builder()
                .id(1L)
                .nome("Barbearia Teste")
                .cnpj("0123456789101112")
                .endereco("Rua Teste, 12")
                .build();
    }

    public static Barbearia montarBarbearia(Long id, String nome) {
        return Barbearia.builder()
                .id(id)
                .nome(nome)
                .cnpj("0123456789101112")
                .endereco("Rua Teste, 12")
                .build();
    }

    public static BarbeariaRespostaDto montarBarbeariaRespostaDto(UUID idExterno) {
        return BarbeariaRespostaDto.builder()
                .idExterno(idExterno)
                .nome("Barbearia Teste")
                .cnpj("0123456789101112")
                .endereco("Rua Teste, 12")
                .telefone("988888888")
                .build();
    }

    public static LoginRequestDto montarLoginRequestDto() {
        return LoginRequestDto.builder()
                .email("teste@mail.com")
                .senha("teste")
                .build();
    }

    public static UsuarioCadastrarDto montarUsuarioBarbearia(Barbearia barbearia) {
        return UsuarioCadastrarDto.builder()
                .email("teste@mail.com")
                .senha("teste")
                .role(Role.BARBEARIA)
                .data(barbearia)
                .build();
    }

    public static Barbeiro montarBarbeiro() {
        return Barbeiro.builder()
                .id(1L)
                .cpf("01345678910")
                .nome("Barbeiro Teste")
                .admin(false)
                .celular("988888888")
                .ativo(true)
                .barbearia(montarBarbearia())
                .usuario(montarUsuario(Role.BARBEIRO))
                .build();
    }

    public static BarbeiroDto montarBarbeiroDto(UUID idExternoBarbeiro) {
        return BarbeiroDto.builder()
                .idExterno(idExternoBarbeiro)
                .nome("Barbeiro Teste")
                .ativo(true)
                .admin(false)
                .cpf("0134567891011")
                .celular("988888888")
                .build();
    }

    public static BarbeiroDto montarBarbeiroDto(UUID idExternoBarbeiro, String nome) {
        return BarbeiroDto.builder()
                .idExterno(idExternoBarbeiro)
                .nome(nome)
                .ativo(true)
                .admin(false)
                .cpf("0134567891011")
                .celular("988888888")
                .build();
    }

    public static BarbeiroAgendamentoDto montarBarbeiroAgendamentoDto() {
        return BarbeiroAgendamentoDto.builder()
                .idExterno(UUID.randomUUID())
                .nome("Barbeiro Teste")
                .ativo(true)
                .celular("988888888")
                .build();
    }

    public static BarbeariaCadastroDto montarBarbeariaCadastroDto() {
        return BarbeariaCadastroDto.builder()
                .nome("Barbearia Teste")
                .cnpj("01234567891011")
                .endereco("Rua Teste, 123")
                .telefone("988888888")
                .build();
    }

    public static BarbeiroAtualizarDto montarBarbeiroAtualizarDto() {
        return BarbeiroAtualizarDto.builder()
                .id(1L)
                .nome("Barbeiro Atualizar")
                .celular("988888889")
                .admin(true)
                .ativo(true)
                .build();
    }

    public static Usuario montarUsuario() {
        return new Usuario(
                1L,
                UUID.randomUUID(),
                EMAIL_USUARIO,
                SENHA_USUARIO,
                Role.BARBEARIA
        );
    }

    public static Usuario montarUsuario(Role role) {
        return new Usuario(
                1L,
                UUID.randomUUID(),
                EMAIL_USUARIO,
                SENHA_USUARIO,
                role
        );
    }

    public static Usuario montarUsuario(Role role, String senha) {
        String senhaCriptografada = bCryptPasswordEncoder.encode(senha);

        return new Usuario(
                1L,
                UUID.randomUUID(),
                EMAIL_USUARIO,
                senhaCriptografada,
                role
        );
    }

    public static UsuarioCadastrarDto montarUsuarioCadastrarDto() {
        return UsuarioCadastrarDto.builder()
                .email(EMAIL_USUARIO)
                .senha(SENHA_USUARIO)
                .role(Role.BARBEARIA)
                .build();
    }

    public static BarbeiroCadastroDto montarBarbeiroCadastroDto() {
        return BarbeiroCadastroDto.builder()
                .admin(false)
                .ativo(true)
                .celular("988888888")
                .nome("Barbeiro Teste")
                .usuario(montarUsuario())
                .cpf("013456789101")
                .build();
    }

    public static Servico montarServico() {
        return Servico.builder()
                .id(1L)
                .nome("Serviço Teste")
                .ativo(true)
                .preco(new BigDecimal("30.0"))
                .tempoDuracaoEmMinutos(30)
                .barbearia(montarBarbearia())
                .build();
    }

    public static Servico montarServico(String nome) {
        return Servico.builder()
                .id(1L)
                .nome(nome)
                .ativo(true)
                .preco(new BigDecimal("50.0"))
                .tempoDuracaoEmMinutos(30)
                .build();
    }

    public static Servico montarServico(Barbearia barbearia) {
        return Servico.builder()
                .id(1L)
                .nome("Serviço Teste")
                .ativo(true)
                .tempoDuracaoEmMinutos(45)
                .preco(new BigDecimal("45"))
                .barbearia(barbearia)
                .build();
    }

    public static ServicoDto montarServicoDto() {
        return ServicoDto.builder()
                .idExterno(UUID.randomUUID())
                .nome("Serviço Teste")
                .ativo(true)
                .preco(new BigDecimal("30.0"))
                .tempoDuracaoEmMinutos(45)
                .build();
    }

    public static ServicoDto montarServicoDto(UUID idExterno) {
        return ServicoDto.builder()
                .idExterno(idExterno)
                .nome("Serviço Teste")
                .tempoDuracaoEmMinutos(45)
                .preco(new BigDecimal("30.0"))
                .ativo(true)
                .build();
    }

    public static ServicoDto montarServicoDto(UUID idExterno, String nome) {
        return ServicoDto.builder()
                .idExterno(idExterno)
                .nome(nome)
                .tempoDuracaoEmMinutos(45)
                .preco(new BigDecimal("30.0"))
                .ativo(true)
                .build();
    }

    public static ServicoAtualizarDto montarServicoAtualizarDto() {
        return ServicoAtualizarDto.builder()
                .id(1L)
                .nome("Serviço Atualizar")
                .preco(new BigDecimal("50.0"))
                .tempoDuracaoEmMinutos(45)
                .ativo(false)
                .build();
    }

    public static ServicoCadastroDto montarServicoCadastroDto() {
        return ServicoCadastroDto.builder()
                .nome("Serviço Teste")
                .preco(new BigDecimal("50.0"))
                .tempoDuracaoEmMinutos(45)
                .ativo(true)
                .idExternoBarbearia(UUID.randomUUID())
                .build();
    }

    public static Cliente montarCliente() {
        return new Cliente(
                1L,
                UUID.randomUUID(),
                "Cliente Teste",
                LocalDate.of(2001, 1, 1),
                "+5588988888888",
                Collections.emptySet()
        );
    }

    public static Cliente montarCliente(Long id, String nome) {
        return new Cliente(
                id,
                nome,
                LocalDate.of(2001, 1, 1),
                "+5588988888888",
                Collections.emptySet()
        );
    }

    public static ClienteDto montarClienteDto(UUID idExterno) {
        return ClienteDto.builder()
                .idExterno(idExterno)
                .nome("Cliente Teste")
                .celular("988888888")
                .dataNascimento(LocalDate.of(2001, 1, 1))
                .build();
    }

    public static ClienteCadastroDto montarClienteCadastroDto() {
        return ClienteCadastroDto.builder()
                .nome("Cliente Teste")
                .celular("988888888")
                .dataNascimento(LocalDate.of(2001, 1, 1))
                .build();
    }

    public static Agendamento montarAgendamento(Long id, ZonedDateTime dataHora, BigDecimal valorTotal) {
        return Agendamento.builder()
                .id(id)
                .dataHora(dataHora)
                .tempoDuracaoEmMinutos(45)
                .valorTotal(valorTotal)
                .barbearia(montarBarbearia())
                .build();
    }

    public static AgendamentoDto montarAgendamentoDto(ZonedDateTime dataHora, Integer tempoDuracaoEmMinutos, BigDecimal valorTotal, UUID idExternoBarbearia, UUID idExternoCliente) {
        return AgendamentoDto.builder()
                .idExterno(UUID.randomUUID())
                .cliente(montarClienteDto(idExternoCliente))
                .barbearia(montarBarbeariaRespostaDto(idExternoBarbearia))
                .servicos(Set.of(montarServicoDto()))
                .barbeiros(Set.of(montarBarbeiroAgendamentoDto()))
                .dataHora(dataHora)
                .tempoDuracaoEmMinutos(tempoDuracaoEmMinutos)
                .valorTotal(valorTotal)
                .build();
    }

    public static AgendamentoDto montarAgendamentoDto(BarbeariaRespostaDto barbearia, ClienteDto cliente, ServicoDto servico, BarbeiroAgendamentoDto barbeiro) {
        return AgendamentoDto.builder()
                .idExterno(UUID.randomUUID())
                .dataHora(ZonedDateTime.now())
                .tempoDuracaoEmMinutos(45)
                .valorTotal(new BigDecimal("75.50"))
                .barbearia(barbearia)
                .cliente(cliente)
                .servicos(Set.of(servico))
                .barbeiros(Set.of(barbeiro))
                .build();
    }

    public static AgendamentoCadastroDto montarAgendamentoCadastroDto(BigDecimal valorTotal, Integer tempoDuracaoEmMinutos, ZonedDateTime dataHora) {
        return AgendamentoCadastroDto.builder()
                .valorTotal(valorTotal)
                .tempoDuracaoEmMinutos(tempoDuracaoEmMinutos)
                .dataHora(dataHora)
                .clienteIdExterno(UUID.randomUUID())
                .barbeariaIdExterno(UUID.randomUUID())
                .servicosIdsExterno(List.of(UUID.randomUUID()))
                .barbeirosIdsExterno(List.of(UUID.randomUUID()))
                .build();
    }

    public static AgendamentoAtualizarDto montarAgendamentoAtualizarDto(
            Long agendamentoId, ZonedDateTime dataHora, BigDecimal valorTotal, Integer tempoDuracaoEmMinutos) {
        return AgendamentoAtualizarDto.builder()
                .id(agendamentoId)
                .dataHora(dataHora)
                .valorTotal(valorTotal)
                .tempoDuracaoEmMinutos(tempoDuracaoEmMinutos)
                .build();
    }

    public static AgendamentoRespostaDto montarAgendamentoRespostaDto(
            Long id, ZonedDateTime dataHora, BigDecimal valorTotal, UUID idExternoBarbearia, UUID idExternoCliente) {
        return AgendamentoRespostaDto.builder()
                .id(id)
                .dataHora(dataHora)
                .tempoDuracaoEmMinutos(45)
                .valorTotal(valorTotal)
                .barbearia(montarBarbeariaDto(idExternoBarbearia))
                .cliente(montarClienteDto(idExternoCliente))
                .servicos(Set.of(montarServico()))
                .barbeiros(Set.of(montarBarbeiroAgendamentoDto()))
                .build();
    }

    public static AgendamentoReduzidoDto montarAgendamentoReduzidoDto() {
        return AgendamentoReduzidoDto.builder()
                .idExterno(UUID.randomUUID())
                .nomeCliente("Nome Cliente")
                .dataHoraInicio(ZonedDateTime.now())
                .dataHoraFim(ZonedDateTime.now().plusMinutes(30))
                .servicos(List.of("Serviço 1"))
                .build();
    }

    public static RespostaLogin montarRespostaLoginSucesso(Object result, String accessToken) {
        return RespostaLogin.builder()
                .success(true)
                .message(Constants.Success.LOGIN_REALIZADO_COM_SUCESSO)
                .accessToken(accessToken)
                .result(result)
                .build();
    }

}
