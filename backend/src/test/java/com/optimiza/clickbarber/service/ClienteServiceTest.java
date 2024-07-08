package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.cliente.Cliente;
import com.optimiza.clickbarber.model.cliente.dto.ClienteCadastroDto;
import com.optimiza.clickbarber.model.cliente.dto.ClienteMapper;
import com.optimiza.clickbarber.repository.ClienteRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    private Long usuarioId;
    private UUID idExternoCliente;

    @BeforeEach
    void setup() {
        usuarioId = 1L;
        idExternoCliente = UUID.randomUUID();
    }

    @Test
    void testBuscarClientePorUsuarioId_Encontrado() {
        var cliente = montarCliente();
        when(clienteRepository.findByUsuarioId(anyLong())).thenReturn(Optional.of(cliente));

        var clienteDto = montarClienteDto(idExternoCliente);
        when(clienteMapper.toDto(any(Cliente.class))).thenReturn(clienteDto);

        var clienteEncontradoResult = clienteService.buscarPorUsuarioId(usuarioId);
        assertNotNull(clienteEncontradoResult);
        assertEquals(1, cliente.getId());
        assertEquals("Cliente Teste", cliente.getNome());
    }

    @Test
    void testBuscarClientePorUsuarioId_NaoEncontrado() {
        when(clienteRepository.findByUsuarioId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorUsuarioId(usuarioId));
    }

    @Test
    void testBuscarClientePorId_Encontrado() {
        var cliente = montarCliente();
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));

        var clienteEncontradoResult = clienteService.buscarPorId(1L);
        assertNotNull(clienteEncontradoResult);
        assertEquals(1, clienteEncontradoResult.getId());
        assertEquals("Cliente Teste", clienteEncontradoResult.getNome());
    }

    @Test
    void testBuscarClientePorId_NaoEncontrado() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorId(1L));
    }

    @Test
    void testBuscarClientesPeloIdExternoDaBarbearia() {
        var cliente1 = montarCliente();
        var cliente2 = montarCliente(2L, "Cliente Teste 2");
        var listaClientes = List.of(cliente1, cliente2);
        when(clienteRepository.findByIdExternoBarbearia(any(UUID.class))).thenReturn(listaClientes);

        var idExternoCliente1 = UUID.randomUUID();
        var clienteDto1 = montarClienteDto(idExternoCliente1);
        when(clienteMapper.toDto(cliente1)).thenReturn(clienteDto1);

        var idExternoCliente2 = UUID.randomUUID();
        var clienteDto2 = montarClienteDto(idExternoCliente2);
        when(clienteMapper.toDto(cliente2)).thenReturn(clienteDto2);

        var idExternoBarbearia = UUID.randomUUID();
        var clientesEncontrados = clienteService.buscarPorIdExternoBarbearia(idExternoBarbearia);
        assertFalse(clientesEncontrados.isEmpty());
        assertEquals(idExternoCliente1, clientesEncontrados.getFirst().getIdExterno());
        assertEquals(idExternoCliente2, clientesEncontrados.getLast().getIdExterno());
    }

    @Test
    void testCadastrarCliente() {
        var cliente = montarCliente();
        when(clienteMapper.toEntity(any(ClienteCadastroDto.class))).thenReturn(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        var clienteDto = montarClienteDto(idExternoCliente);
        when(clienteMapper.toDto(any(Cliente.class))).thenReturn(clienteDto);

        var clienteCadastroDto = montarClienteCadastroDto();
        var clienteCadastradoResult = clienteService.cadastrar(clienteCadastroDto);
        assertNotNull(clienteCadastradoResult);
        assertEquals(idExternoCliente, clienteCadastradoResult.getIdExterno());
        assertEquals("Cliente Teste", clienteCadastradoResult.getNome());
    }

}
