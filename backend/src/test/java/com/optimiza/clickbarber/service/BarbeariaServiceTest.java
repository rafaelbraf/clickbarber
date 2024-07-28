package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.barbearia.Barbearia;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaCadastroDto;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaMapper;
import com.optimiza.clickbarber.repository.BarbeariaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BarbeariaServiceTest {

    @InjectMocks
    private BarbeariaService barbeariaService;

    @Mock
    private BarbeariaRepository barbeariaRepository;

    @Mock
    private BarbeariaMapper barbeariaMapper;

    private Long usuarioId;
    private UUID barbeariaIdExterno;

    @BeforeEach
    void setup() {
        usuarioId = 1L;
        barbeariaIdExterno = UUID.randomUUID();
    }

    @Test
    void testVerificaSeBarbeariaExiste() {
        when(barbeariaRepository.existsById(anyLong())).thenReturn(true);
        var existeBarbearia = barbeariaService.existePorId(1L);
        assertTrue(existeBarbearia);
    }

    @Test
    void testBuscarTodasAsBarbeariasComLimite() {
        var barbearia1 = montarBarbearia();
        var barbeariasLista = List.of(barbearia1);

        var barbeariasPage = new PageImpl<>(barbeariasLista);
        var pageable = PageRequest.of(0, 1);
        when(barbeariaRepository.findAll(pageable)).thenReturn(barbeariasPage);

        var barbeariaDto1 = montarBarbeariaDto(barbeariaIdExterno);
        when(barbeariaMapper.toDto(barbearia1)).thenReturn(barbeariaDto1);

        var barbeariasEncontradas = barbeariaService.buscarTodos(pageable);
        assertFalse(barbeariasEncontradas.isEmpty());
        assertEquals(1, barbeariasEncontradas.size());
        assertEquals(barbeariaIdExterno, barbeariasEncontradas.getFirst().getIdExterno());
        assertEquals("Barbearia Teste", barbeariasEncontradas.getFirst().getNome());

        verify(barbeariaRepository, times(1)).findAll(pageable);
        verify(barbeariaMapper, times(barbeariasLista.size())).toDto(any(Barbearia.class));
    }

    @Test
    void testBuscarTodasAsBarbeariaSemLimite() {
        var nomeBarbearia2 = "Barbearia Teste 2";
        var barbearia1 = montarBarbearia();
        var barbearia2 = montarBarbearia(2L, nomeBarbearia2);
        var barbeariasLista = List.of(barbearia1, barbearia2);
        when(barbeariaRepository.findAll()).thenReturn(barbeariasLista);

        var barbeariaDto1 = montarBarbeariaDto(barbeariaIdExterno);
        when(barbeariaMapper.toDto(barbearia1)).thenReturn(barbeariaDto1);

        var barbeariaIdExterno2 = UUID.randomUUID();
        var barbeariaDto2 = montarBarbeariaDto(barbeariaIdExterno2, nomeBarbearia2);
        when(barbeariaMapper.toDto(barbearia2)).thenReturn(barbeariaDto2);

        var barbeariasEncontradas = barbeariaService.buscarTodos(Pageable.unpaged());
        assertFalse(barbeariasEncontradas.isEmpty());
        assertEquals(2, barbeariasEncontradas.size());
        assertEquals(barbeariaIdExterno, barbeariasEncontradas.getFirst().getIdExterno());
        assertEquals("Barbearia Teste", barbeariasEncontradas.getFirst().getNome());
        assertEquals(barbeariaIdExterno2, barbeariasEncontradas.getLast().getIdExterno());
        assertEquals("Barbearia Teste 2", barbeariasEncontradas.getLast().getNome());

        verify(barbeariaRepository, times(1)).findAll();
        verify(barbeariaMapper, times(barbeariasLista.size())).toDto(any(Barbearia.class));
    }

    @Test
    void testBuscarBarbeariaPorId_Encontrado() {
        var barbearia = montarBarbearia();
        when(barbeariaRepository.findById(anyLong())).thenReturn(Optional.of(barbearia));

        var barbeariaEncontrada = barbeariaService.buscarPorId(1L);
        assertNotNull(barbeariaEncontrada);
        assertEquals(1, barbeariaEncontrada.getId());
        assertEquals("Barbearia Teste", barbeariaEncontrada.getNome());
    }

    @Test
    void testBuscarBarbeariaPorId_NaoEncontrado() {
        when(barbeariaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> barbeariaService.buscarPorId(1L));
    }

    @Test
    void testBuscarBarbeariaPorUsuarioId_Encontrado() {
        var barbearia = montarBarbearia();
        when(barbeariaRepository.findByUsuarioId(anyLong())).thenReturn(Optional.of(barbearia));

        var barbeariaRespostaDto = montarBarbeariaRespostaDto(barbeariaIdExterno);
        when(barbeariaMapper.toRespostaDto(any(Barbearia.class))).thenReturn(barbeariaRespostaDto);

        var barbeariaEncontrada = barbeariaService.buscarPorUsuarioId(usuarioId);
        assertNotNull(barbeariaEncontrada);
        assertEquals(barbeariaIdExterno, barbeariaEncontrada.getIdExterno());
        assertEquals("Barbearia Teste", barbeariaEncontrada.getNome());
    }

    @Test
    void testBuscarBarbeariaPorUsuarioId_NaoEncontrado() {
        when(barbeariaRepository.findByUsuarioId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> barbeariaService.buscarPorUsuarioId(usuarioId));
    }

    @Test
    void testBuscarBarbeariaPeloNome() {
        var barbearia1 = montarBarbearia();
        var barbearia2 = montarBarbearia(2L, "Barbearia Teste 2");
        var barbeariasLista = List.of(barbearia1, barbearia2);
        when(barbeariaRepository.findByNomeContainingIgnoreCase(anyString())).thenReturn(barbeariasLista);

        var barbearia1Dto = montarBarbeariaDto(barbeariaIdExterno);
        when(barbeariaMapper.toDto(barbearia1)).thenReturn(barbearia1Dto);

        var barbeariaIdExterno2 = UUID.randomUUID();
        var barbearia2Dto = montarBarbeariaDto(barbeariaIdExterno2, "Barbearia Teste 2");
        when(barbeariaMapper.toDto(barbearia2)).thenReturn(barbearia2Dto);

        var barbeariasEncontradas = barbeariaService.buscarPorNome("Barbearia");
        assertFalse(barbeariasEncontradas.isEmpty());
        assertEquals(2, barbeariasEncontradas.size());
        assertEquals(barbeariaIdExterno, barbeariasEncontradas.getFirst().getIdExterno());
        assertEquals("Barbearia Teste", barbeariasEncontradas.getFirst().getNome());
        assertEquals(barbeariaIdExterno2, barbeariasEncontradas.getLast().getIdExterno());
        assertEquals("Barbearia Teste 2", barbeariasEncontradas.getLast().getNome());
    }

    @Test
    void testCadastrarBarbearia() {
        var barbearia = montarBarbearia();
        when(barbeariaMapper.toEntity(any(BarbeariaCadastroDto.class))).thenReturn(barbearia);
        when(barbeariaRepository.save(any(Barbearia.class))).thenReturn(barbearia);

        var barbeariaDto = montarBarbeariaDto(barbeariaIdExterno);
        when(barbeariaMapper.toDto(any(Barbearia.class))).thenReturn(barbeariaDto);

        var barbeariaRespostaDto = montarBarbeariaRespostaDto(barbeariaIdExterno);
        when(barbeariaMapper.toRespostaDto(any(Barbearia.class))).thenReturn(barbeariaRespostaDto);

        var barbeariaCadastroDto = montarBarbeariaCadastroDto();
        var barbeariaCadastradaResult = barbeariaService.cadastrar(barbeariaCadastroDto);
        assertNotNull(barbeariaCadastradaResult);
        assertEquals(barbeariaIdExterno, barbeariaCadastradaResult.getIdExterno());
        assertEquals("Barbearia Teste", barbeariaCadastradaResult.getNome());
    }

}
