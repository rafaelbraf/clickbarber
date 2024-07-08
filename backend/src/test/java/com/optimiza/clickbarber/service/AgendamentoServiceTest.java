package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.EntidadeNaoPerteceABarbeariaException;
import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.Agendamento;
import com.optimiza.clickbarber.model.dto.agendamento.AgendamentoMapper;
import com.optimiza.clickbarber.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private AgendamentoMapper agendamentoMapper;

    @Mock
    private BarbeariaService barbeariaService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ServicoService servicoService;

    @Mock
    private BarbeiroService barbeiroService;

    private Long agendamentoId;
    private ZonedDateTime dataHoraAgendamento;
    private BigDecimal valorTotalAgendamento;
    private UUID barbeariaIdExterno;
    private UUID clienteIdExterno;

    @BeforeEach
    void setup() {
        agendamentoId = 1L;
        dataHoraAgendamento = ZonedDateTime.now();
        valorTotalAgendamento = new BigDecimal(50.0);
        barbeariaIdExterno = UUID.randomUUID();
        clienteIdExterno = UUID.randomUUID();
    }

    @Test
    void testBuscarAgendamentoPorId_Encontrado() {
        var agendamento = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.of(agendamento));

        var agendamentoDto = montarAgendamentoDto(
                1L, dataHoraAgendamento, 45, valorTotalAgendamento, barbeariaIdExterno, clienteIdExterno);
        when(agendamentoMapper.toDto(any(Agendamento.class))).thenReturn(agendamentoDto);

        var agendamentoEncontrado = agendamentoService.buscarPorId(agendamentoId);
        assertNotNull(agendamentoEncontrado);
        assertEquals(agendamentoId, agendamentoEncontrado.getId());
        assertEquals(dataHoraAgendamento, agendamentoEncontrado.getDataHora());
        assertEquals(valorTotalAgendamento, agendamentoEncontrado.getValorTotal());
        assertEquals(45, agendamentoEncontrado.getTempoDuracaoEmMinutos());
        assertEquals(barbeariaIdExterno, agendamentoEncontrado.getBarbearia().getIdExterno());
        assertEquals(clienteIdExterno, agendamentoEncontrado.getCliente().getIdExterno());
        assertFalse(agendamentoEncontrado.getServicos().isEmpty());
        assertFalse(agendamentoEncontrado.getBarbeiros().isEmpty());
    }

    @Test
    void testBuscarAgendamentoPorId_NaoEncontrado() {
        when(agendamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> agendamentoService.buscarPorId(agendamentoId));
    }

    @Test
    void testBuscarAgendamentosPorBarbeariaId() {
        var agendamento1 = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        var agendamento2 = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        var agendamentos = List.of(agendamento1, agendamento2);
        when(agendamentoRepository.findByIdExternoBarbearia(any(UUID.class))).thenReturn(agendamentos);

        var agendamentoRespostaDto = montarAgendamentoRespostaDto(1L, dataHoraAgendamento, valorTotalAgendamento, barbeariaIdExterno, clienteIdExterno);
        when(agendamentoMapper.toAgendamentoRespostaDto(any(Agendamento.class))).thenReturn(agendamentoRespostaDto);

        var agendamentosLista = agendamentoService.buscarPorIdExternoBarbearia(barbeariaIdExterno);
        assertFalse(agendamentosLista.isEmpty());
        assertEquals(barbeariaIdExterno, agendamentosLista.getFirst().getBarbearia().getIdExterno());
        assertEquals(barbeariaIdExterno, agendamentosLista.getLast().getBarbearia().getIdExterno());
    }

    @Test
    void testBuscarAgendamentosComInformacoesReduzidasPorIdExternoBarbearia() {
        var agendamento1 = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        var agendamento2 = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        var agendamentos = List.of(agendamento1, agendamento2);
        when(agendamentoRepository.findByIdExternoBarbearia(any(UUID.class))).thenReturn(agendamentos);

        var agendamentoReduzidoDto1 = montarAgendamentoReduzidoDto();
        when(agendamentoMapper.toAgendamentoReduzidoDto(agendamento1)).thenReturn(agendamentoReduzidoDto1);

        var agendamentoReduzidoDto2 = montarAgendamentoReduzidoDto();
        when(agendamentoMapper.toAgendamentoReduzidoDto(agendamento2)).thenReturn(agendamentoReduzidoDto2);

        var agendamentosEncontrados = agendamentoService.buscarReduzidoPorIdExternoBarberia(barbeariaIdExterno);
        assertFalse(agendamentosEncontrados.isEmpty());
        assertEquals("Nome Cliente", agendamentosEncontrados.getFirst().getNomeCliente());
        assertEquals("Nome Cliente", agendamentosEncontrados.getLast().getNomeCliente());
        assertFalse(agendamentosEncontrados.getFirst().getServicos().isEmpty());
        assertFalse(agendamentosEncontrados.getLast().getServicos().isEmpty());
    }

    @Test
    void testBuscarAgendamentoPorBarbeariaIdEDataHora() {
        var agendamento1 = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        var agendamento2 = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        var agendamentos = List.of(agendamento1, agendamento2);
        when(agendamentoRepository.findByDataHoraAndBarbeariaId(any(ZonedDateTime.class), anyInt())).thenReturn(agendamentos);

        var agendamentosEncontrados = agendamentoService.buscarPorBarbeariaIdEDataHora(1, dataHoraAgendamento);
        assertFalse(agendamentosEncontrados.isEmpty());
        assertEquals(1, agendamentosEncontrados.getFirst().getBarbearia().getId());
        assertEquals(dataHoraAgendamento, agendamentosEncontrados.getFirst().getDataHora());
        assertEquals(1, agendamentosEncontrados.getLast().getBarbearia().getId());
        assertEquals(dataHoraAgendamento, agendamentosEncontrados.getLast().getDataHora());
    }

    @Test
    void testCadastrarAgendamento() {
        var barbearia = montarBarbearia();
        when(barbeariaService.buscarPorId(anyLong())).thenReturn(barbearia);

        var cliente = montarCliente();
        when(clienteService.buscarPorId(anyLong())).thenReturn(cliente);

        var servico = montarServico();
        when(servicoService.buscarPorId(anyLong())).thenReturn(servico);

        var barbeiro = montarBarbeiro();
        when(barbeiroService.buscarPorId(anyLong())).thenReturn(barbeiro);

        var agendamento = montarAgendamento(agendamentoId, dataHoraAgendamento, valorTotalAgendamento);
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        var agendamentoDto = montarAgendamentoDto(
                1L, dataHoraAgendamento, 45, valorTotalAgendamento, barbeariaIdExterno, clienteIdExterno);
        when(agendamentoMapper.toDto(any(Agendamento.class))).thenReturn(agendamentoDto);

        var agendamentoCadastroDto = montarAgendamentoCadastroDto(valorTotalAgendamento, 45, dataHoraAgendamento);
        var agendamentoCadastrado = agendamentoService.cadastrar(agendamentoCadastroDto);
        assertNotNull(agendamentoCadastrado);
        assertEquals(dataHoraAgendamento, agendamentoCadastrado.getDataHora());
        assertEquals("50", agendamentoCadastrado.getValorTotal().toString());
        assertEquals(45, agendamentoCadastrado.getTempoDuracaoEmMinutos());
        assertEquals(barbeariaIdExterno, agendamentoCadastrado.getBarbearia().getIdExterno());
        assertEquals(clienteIdExterno, agendamentoCadastrado.getCliente().getIdExterno());
        assertFalse(agendamentoCadastrado.getServicos().isEmpty());
        assertFalse(agendamentoCadastrado.getBarbeiros().isEmpty());
    }

    @Test
    void testCadastrarAgendamentoComServicoQueNaoPertenceABarbearia() {
        var barbearia = montarBarbearia(2L, "Barbearia Teste");
        when(barbeariaService.buscarPorId(anyLong())).thenReturn(barbearia);

        var cliente = montarCliente();
        when(clienteService.buscarPorId(anyLong())).thenReturn(cliente);

        var servico = montarServico();
        when(servicoService.buscarPorId(anyLong())).thenReturn(servico);

        var agendamentoCadastroDto = montarAgendamentoCadastroDto(valorTotalAgendamento, 45, dataHoraAgendamento);
        assertThrows(EntidadeNaoPerteceABarbeariaException.class, () -> agendamentoService.cadastrar(agendamentoCadastroDto));
    }

    @Test
    void testCadastrarAgendamentoComBarbeiroQueNaoPertenceABarbearia() {
        var barbearia = montarBarbearia(2L, "Barbearia Teste");
        when(barbeariaService.buscarPorId(anyLong())).thenReturn(barbearia);

        var cliente = montarCliente();
        when(clienteService.buscarPorId(anyLong())).thenReturn(cliente);

        var servico = montarServico(barbearia);
        servico.setBarbearia(barbearia);
        when(servicoService.buscarPorId(anyLong())).thenReturn(servico);

        var barbeiro = montarBarbeiro();
        when(barbeiroService.buscarPorId(anyLong())).thenReturn(barbeiro);

        var agendamentoCadastroDto = montarAgendamentoCadastroDto(valorTotalAgendamento, 45, dataHoraAgendamento);
        assertThrows(EntidadeNaoPerteceABarbeariaException.class, () -> agendamentoService.cadastrar(agendamentoCadastroDto));
    }

    @Test
    void testDeletarAgendamentoPorId() {
        agendamentoService.deletarPorId(agendamentoId);
        verify(agendamentoRepository, times(1)).deleteById(anyLong());
    }

}
