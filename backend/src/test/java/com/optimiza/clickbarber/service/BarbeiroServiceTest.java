package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroCadastroDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroMapper;
import com.optimiza.clickbarber.repository.BarbeiroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BarbeiroServiceTest {

    @InjectMocks
    private BarbeiroService barbeiroService;

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @Mock
    private BarbeariaService barbeariaService;

    @Mock
    private BarbeiroMapper barbeiroMapper;

    private UUID barbeiroIdExterno;
    private UUID barbeariaIdExterno;

    @BeforeEach
    void setup() {
        barbeiroIdExterno = UUID.randomUUID();
        barbeariaIdExterno = UUID.randomUUID();
    }

    @Test
    void testBuscarPorId_Encontrado() {
        var barbeiroEncontrado = montarBarbeiro();

        when(barbeiroRepository.findById(anyLong())).thenReturn(Optional.of(barbeiroEncontrado));

        var barbeiroResult = barbeiroService.buscarPorId(1L);

        assertNotNull(barbeiroResult);
        assertEquals(1, barbeiroResult.getId());
        assertEquals("Barbeiro Teste", barbeiroResult.getNome());
        assertEquals(1, barbeiroResult.getBarbearia().getId());
    }

    @Test
    void testBuscarPorId_NaoEncontrado() {
        when(barbeiroRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> barbeiroService.buscarPorId(1L));
    }

    @Test
    void testBuscarPorBarbeariaId_Encontrados() {
        var barbeiro1 = montarBarbeiro();
        var barbeiro2 = montarBarbeiro();
        barbeiro2.setId(2L);
        var barbeirosEncontrados = List.of(barbeiro1, barbeiro2);
        when(barbeiroRepository.findByIdExternoBarbearia(any(UUID.class))).thenReturn(barbeirosEncontrados);

        var barbeiroDto1 = montarBarbeiroDto(barbeiroIdExterno);
        when(barbeiroMapper.toDto(barbeiro1)).thenReturn(barbeiroDto1);

        var idExternoBarbeiroDto2 = UUID.randomUUID();
        var barbeiroDto2 = montarBarbeiroDto(idExternoBarbeiroDto2, "Barbeiro Teste 2");
        when(barbeiroMapper.toDto(barbeiro2)).thenReturn(barbeiroDto2);

        var barbeirosResult = barbeiroService.buscarPorIdExternoBarbearia(barbeariaIdExterno);

        assertFalse(barbeirosResult.isEmpty());
        assertEquals(barbeiroIdExterno, barbeirosResult.getFirst().getIdExterno());
        assertEquals("Barbeiro Teste", barbeirosResult.getFirst().getNome());
        assertEquals(idExternoBarbeiroDto2, barbeirosResult.getLast().getIdExterno());
        assertEquals("Barbeiro Teste 2", barbeirosResult.getLast().getNome());
    }

    @Test
    void testBuscarPorBarbeariaId_NaoEncontrados() {
        when(barbeiroRepository.findByBarbeariaId(anyInt())).thenReturn(Collections.emptyList());

        var barbeirosResult = barbeiroService.buscarPorIdExternoBarbearia(barbeariaIdExterno);

        assertTrue(barbeirosResult.isEmpty());
    }

    @Test
    void testBuscarPorUsuarioId_Encontrado() {
        var barbeiro = montarBarbeiro();
        when(barbeiroRepository.findByUsuarioId(anyLong())).thenReturn(Optional.of(barbeiro));

        var barbeiroDto = montarBarbeiroDto(barbeiroIdExterno);
        when(barbeiroMapper.toDto(any(Barbeiro.class))).thenReturn(barbeiroDto);

        var barbeiroResult = barbeiroService.buscarPorUsuarioId(1L);

        assertNotNull(barbeiroResult);
        assertEquals(barbeiroIdExterno, barbeiroResult.getIdExterno());
        assertEquals("Barbeiro Teste", barbeiroResult.getNome());
    }

    @Test
    void testBuscarPorUsuarioId_NaoEncontrados() {
        when(barbeiroRepository.findByUsuarioId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> barbeiroService.buscarPorUsuarioId(1L));
    }

    @Test
    void testCadastrarBarbeiro() {
        when(barbeariaService.existePorId(anyLong())).thenReturn(true);

        var barbearia = montarBarbearia();
        when(barbeariaService.buscarPorId(anyLong())).thenReturn(barbearia);

        var barbeiro = montarBarbeiro();
        when(barbeiroMapper.toEntity(any(BarbeiroCadastroDto.class))).thenReturn(barbeiro);
        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiro);

        var barbeiroDto = montarBarbeiroDto(barbeiroIdExterno);
        when(barbeiroMapper.toDto(any(Barbeiro.class))).thenReturn(barbeiroDto);

        var barbeiroCadastroDto = montarBarbeiroCadastroDto();
        var barbeiroCadastradoResult = barbeiroService.cadastrar(barbeiroCadastroDto);

        assertNotNull(barbeiroCadastradoResult);
        assertEquals(barbeiroIdExterno, barbeiroCadastradoResult.getIdExterno());
        assertEquals("Barbeiro Teste", barbeiroCadastradoResult.getNome());
    }

    @Test
    void testAtualizarBarbeiro() {
        var barbeiro = montarBarbeiro();
        when(barbeiroRepository.findById(anyLong())).thenReturn(Optional.ofNullable(barbeiro));

        var barbeiroAtualizado = montarBarbeiro();
        barbeiroAtualizado.setNome("Barbeiro Atualizar");
        barbeiroAtualizado.setCelular("988888889");
        barbeiroAtualizado.setAdmin(true);
        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiroAtualizado);

        var barbeiroAtualizarDto = montarBarbeiroAtualizarDto();
        var barbeiroAtualizadoResult = barbeiroService.atualizar(barbeiroAtualizarDto);

        assertNotNull(barbeiroAtualizadoResult);
        assertEquals(1, barbeiroAtualizadoResult.getId());
        assertEquals("Barbeiro Atualizar", barbeiroAtualizarDto.getNome());
        assertEquals("988888889", barbeiroAtualizarDto.getCelular());
    }

    @Test
    void testDeletarBarbeiroPorId() {
        barbeiroService.deletarPorId(1L);
        verify(barbeiroRepository, times(1)).deleteById(anyLong());
    }

}
