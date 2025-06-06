package ec.dev.samagua.ekumen_bank_clients.controllers;

import ec.dev.samagua.commons_lib.models.controllers.ControllerResult;
import ec.dev.samagua.ekumen_bank_clients.dtos.ClienteDto;
import ec.dev.samagua.ekumen_bank_clients.dtos.mappers.ClienteDtoMapper;
import ec.dev.samagua.ekumen_bank_clients.models.Cliente;
import ec.dev.samagua.ekumen_bank_clients.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerUnitTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteDtoMapper clienteDtoMapper;

    @InjectMocks
    private ClienteController clienteController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(clienteController)
                .configureClient()
                .baseUrl("/api/clientes")
                .build();
    }

    @Test
    void search() {
        // Arrange
        when(clienteService.search(any(String.class),any(String.class)))
                .thenReturn(Mono.just(Collections.emptyList()));

        // Act & Assert
        webTestClient.get()
                .uri("?nombre=stalin&cliente-id=1234")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<ControllerResult<List<ClienteDto>>>() {});

        verify(clienteService, times(1)).search(any(String.class),any(String.class));
        verifyNoMoreInteractions(clienteService);
    }

    @Test
    void create() {
        // Arrange
        Cliente cliente = Cliente.builder()
                .id(1L)
                .identificacion(UUID.randomUUID().toString())
                .clienteId(UUID.randomUUID().toString())
                .nombre("Stalin Amagua")
                .direccion("Battle School")
                .telefono("555-8765")
                .clave("1234")
                .estado("TRUE")
                .build();

        ClienteDto clienteDto = ClienteDto.builder()
                .id(1L)
                .identificacion(UUID.randomUUID().toString())
                .clienteId(UUID.randomUUID().toString())
                .nombre("Stalin Amagua")
                .direccion("Battle School")
                .telefono("555-8765")
                .estado("TRUE")
                .build();

        when(clienteDtoMapper.dtoToEntity(any(ClienteDto.class)))
                .thenReturn(Cliente.builder().build());

        when(clienteDtoMapper.entityToDtoObfuscated(any(Cliente.class)))
                .thenReturn(clienteDto);

        when(clienteService.create(any(Cliente.class)))
                .thenReturn(Mono.just(cliente));


        // Act & Assert
        webTestClient.post()
                .uri("")
                .body(Mono.just(ClienteDto.builder().build()), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {});

        verify(clienteService, times(1)).create(any(Cliente.class));
        verifyNoMoreInteractions(clienteService);

    }

    @Test
    void update() {
        // Arrange
        Cliente cliente = Cliente.builder()
                .id(1L)
                .identificacion(UUID.randomUUID().toString())
                .clienteId(UUID.randomUUID().toString())
                .nombre("Stalin Amagua")
                .direccion("Battle School")
                .telefono("555-8765")
                .clave("1234")
                .estado("TRUE")
                .build();

        ClienteDto clienteDto = ClienteDto.builder()
                .id(1L)
                .identificacion(UUID.randomUUID().toString())
                .clienteId(UUID.randomUUID().toString())
                .nombre("Stalin Amagua")
                .direccion("Battle School")
                .telefono("555-8765")
                .estado("TRUE")
                .build();

        when(clienteDtoMapper.dtoToEntity(any(ClienteDto.class)))
                .thenReturn(Cliente.builder().build());

        when(clienteDtoMapper.entityToDtoObfuscated(any(Cliente.class)))
                .thenReturn(clienteDto);

        when(clienteService.update(anyLong(), any(Cliente.class)))
                .thenReturn(Mono.just(cliente));


        // Act & Assert
        webTestClient.put()
                .uri("/1")
                .body(Mono.just(ClienteDto.builder().build()), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {});

        verify(clienteService, times(1)).update(anyLong(), any(Cliente.class));
        verifyNoMoreInteractions(clienteService);
    }

    @Test
    void patch() {
        // Arrange
        Cliente cliente = Cliente.builder()
                .id(1L)
                .identificacion(UUID.randomUUID().toString())
                .clienteId(UUID.randomUUID().toString())
                .nombre("Stalin Amagua")
                .direccion("Battle School")
                .telefono("555-8765")
                .clave("1234")
                .estado("TRUE")
                .build();

        ClienteDto clienteDto = ClienteDto.builder()
                .id(1L)
                .identificacion(UUID.randomUUID().toString())
                .clienteId(UUID.randomUUID().toString())
                .nombre("Stalin Amagua")
                .direccion("Battle School")
                .telefono("555-8765")
                .estado("TRUE")
                .build();

        when(clienteDtoMapper.dtoToEntity(any(ClienteDto.class)))
                .thenReturn(Cliente.builder().build());

        when(clienteDtoMapper.entityToDtoObfuscated(any(Cliente.class)))
                .thenReturn(clienteDto);

        when(clienteService.patch(anyLong(), any(Cliente.class)))
                .thenReturn(Mono.just(cliente));


        // Act & Assert
        webTestClient.patch()
                .uri("/1")
                .body(Mono.just(ClienteDto.builder().build()), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {});

        verify(clienteService, times(1)).patch(anyLong(), any(Cliente.class));
        verifyNoMoreInteractions(clienteService);
    }

    @Test
    void delete() {
        // Arrange
        when(clienteService.delete(anyLong()))
                .thenReturn(Mono.empty());


        // Act & Assert
        webTestClient.delete()
                .uri("/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<ControllerResult<Void>>() {});

        verify(clienteService, times(1)).delete(anyLong());
        verifyNoMoreInteractions(clienteService);
    }
}