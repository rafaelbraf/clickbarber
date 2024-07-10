package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.servico.dto.ServicoCadastroDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoMapper;
import com.optimiza.clickbarber.repository.ServicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ServicoServiceTest {

    @InjectMocks
    private ServicoService servicoService;

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private ServicoMapper servicoMapper;

    @Mock
    private BarbeariaService barbeariaService;

    private Long servicoId;
    private UUID idExternoBarbearia;
    private UUID idExternoServico;

    @BeforeEach
    void setup() {
        servicoId = 1L;
        idExternoBarbearia = UUID.randomUUID();
        idExternoServico = UUID.randomUUID();
    }

    @Test
    void testBuscarTodosOsServicos() {
        var servico = montarServico();
        when(servicoRepository.findAll()).thenReturn(List.of(servico));

        var servicosEncontradosResult = servicoService.buscarTodos();
        assertFalse(servicosEncontradosResult.isEmpty());
        assertEquals("Serviço Teste", servicosEncontradosResult.getFirst().getNome());
        assertEquals(1, servicosEncontradosResult.getFirst().getBarbearia().getId());
    }

    @Test
    void testBuscarServicoPorId_Encontrado() {
        var servico = montarServico();
        when(servicoRepository.findById(anyLong())).thenReturn(Optional.of(servico));

        var servicoEncontradoResult = servicoService.buscarPorId(1L);
        assertNotNull(servicoEncontradoResult);
        assertEquals("Serviço Teste", servicoEncontradoResult.getNome());
        assertEquals(1, servicoEncontradoResult.getBarbearia().getId());
    }

    @Test
    void testBuscarServicoPorId_NaoEncontrado() {
        when(servicoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> servicoService.buscarPorId(servicoId));
    }

    @Test
    void testBuscarPorBarbeariaId() {
        var servico1 = montarServico();
        var servico2 = montarServico("Serviço Teste 2");
        var servicosLista = List.of(servico1, servico2);
        when(servicoRepository.findByIdExternoBarbearia(any(UUID.class))).thenReturn(servicosLista);

        var servico1Dto = montarServicoDto(idExternoServico);
        when(servicoMapper.toDto(servico1)).thenReturn(servico1Dto);

        var idExternoServico2 = UUID.randomUUID();
        var servico2Dto = montarServicoDto(idExternoServico2, "Servico Teste 2");
        when(servicoMapper.toDto(servico2)).thenReturn(servico2Dto);

        var servicosEncontradosResult = servicoService.buscarPorIdExternoBarbearia(idExternoBarbearia);
        assertFalse(servicosEncontradosResult.isEmpty());
        assertEquals(idExternoServico, servicosEncontradosResult.getFirst().getIdExterno());
        assertEquals("Serviço Teste", servicosEncontradosResult.getFirst().getNome());
        assertEquals(idExternoServico2, servicosEncontradosResult.getLast().getIdExterno());
        assertEquals("Serviço Teste", servicosEncontradosResult.getFirst().getNome());
    }

    @Test
    void testCadastrarServico() {
        when(barbeariaService.buscarPorIdExterno(any(UUID.class))).thenReturn(montarBarbearia());

        var servico = montarServico();
        when(servicoMapper.toEntity(any(ServicoCadastroDto.class))).thenReturn(servico);
        when(servicoRepository.save(any(Servico.class))).thenReturn(servico);

        var servicoCadastrarDto = montarServicoCadastroDto();
        var servicoCadastradoResult = servicoService.cadastrar(servicoCadastrarDto);
        assertNotNull(servicoCadastradoResult);
        assertEquals("Serviço Teste", servicoCadastradoResult.getNome());
        assertEquals("30.0", servicoCadastradoResult.getPreco().toString());
        assertEquals(30, servicoCadastradoResult.getTempoDuracaoEmMinutos());
        assertTrue(servicoCadastradoResult.isAtivo());
        assertEquals(1, servicoCadastradoResult.getBarbearia().getId());
    }

    @Test
    void testDeletarServicoPorId() {
        servicoService.deletarPorId(1L);
        verify(servicoRepository, times(1)).deleteById(anyLong());
    }

}
