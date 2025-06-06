package ec.dev.samagua.ekumen_bank_clients.controllers;

import ec.dev.samagua.ekumen_bank_clients.dtos.ClienteDto;
import ec.dev.samagua.commons_lib.models.controllers.ControllerResult;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void search() throws URISyntaxException {
        String identificacion = UUID.randomUUID().toString(); // must be unique
        String clienteId = UUID.randomUUID().toString(); // must be unique
        String nombre = UUID.randomUUID().toString(); // must be unique


        ClienteDto postRequestBody = ClienteDto
                .builder()
                .identificacion(identificacion)
                .clienteId(clienteId)
                .nombre(nombre)
                .direccion("Calle 123 N0. 456 y Calle 789")
                .telefono("555-555")
                .clave("Password1234")
                .estado("TRUE")
                .build();

        EntityExchangeResult<ControllerResult<ClienteDto>> postRequestResult = webTestClient.post().uri("/api/clientes")
                .body(Mono.just(postRequestBody), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {})
                .returnResult();

        ControllerResult<ClienteDto> postResponseBody = postRequestResult.getResponseBody();
        assertThat(postResponseBody).isNotNull();
        assertThat(postResponseBody.getData()).isNotNull();
        assertThat(postResponseBody.getData().getId()).isNotNull();

        URIBuilder uriBuilder = new URIBuilder("/api/clientes")
                .addParameter("nombre", nombre)
                .addParameter("cliente-id", clienteId);

        EntityExchangeResult<ControllerResult<List<ClienteDto>>> getRequestResult = webTestClient.get()
                .uri(uriBuilder.build().toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ControllerResult<List<ClienteDto>>>() {})
                .returnResult();

        ControllerResult<List<ClienteDto>> getResponseBody = getRequestResult.getResponseBody();
        assertThat(getResponseBody).isNotNull();
        assertThat(getResponseBody.getData()).isNotNull();
        assertThat(getResponseBody.getData().size()).isEqualTo(1);
    }

    @Test
    void create() {
        String identificacion = UUID.randomUUID().toString(); // must be unique
        String clienteId = UUID.randomUUID().toString(); // must be unique
        String nombre = UUID.randomUUID().toString(); // must be unique


        ClienteDto postRequestBody = ClienteDto
                .builder()
                .identificacion(identificacion)
                .clienteId(clienteId)
                .nombre(nombre)
                .direccion("Calle 123 N0. 456 y Calle 789")
                .telefono("555-555")
                .clave("Password1234")
                .estado("TRUE")
                .build();

        EntityExchangeResult<ControllerResult<ClienteDto>> postRequestResult = webTestClient.post().uri("/api/clientes")
                .body(Mono.just(postRequestBody), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {})
                .returnResult();

        ControllerResult<ClienteDto> postResponseBody = postRequestResult.getResponseBody();
        assertThat(postResponseBody).isNotNull();
        assertThat(postResponseBody.getData()).isNotNull();
        assertThat(postResponseBody.getData().getId()).isNotNull();

    }

    @Test
    void update() {
        final String identificacion = UUID.randomUUID().toString(); // must be unique
        final String clienteId = UUID.randomUUID().toString(); // must be unique
        final String nombre = UUID.randomUUID().toString(); // must be unique
        final String clave = "Password1234";

        final String originalDireccion = "Calle 123 N0. 456 y Calle 789";
        final String updatedDireccion = "Calle 012 N0. 345 y Calle 678";


        ClienteDto requestBody = ClienteDto
                .builder()
                .identificacion(identificacion)
                .clienteId(clienteId)
                .nombre(nombre)
                .direccion(originalDireccion)
                .telefono("555-555")
                .clave(clave)
                .estado("TRUE")
                .build();

        EntityExchangeResult<ControllerResult<ClienteDto>> postRequestResult = webTestClient.post().uri("/api/clientes")
                .body(Mono.just(requestBody), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {})
                .returnResult();

        ControllerResult<ClienteDto> postResponseBody = postRequestResult.getResponseBody();
        assertThat(postResponseBody).isNotNull();
        assertThat(postResponseBody.getData()).isNotNull();
        assertThat(postResponseBody.getData().getDireccion()).isEqualTo(originalDireccion);

        ClienteDto entityDto = postResponseBody.getData();
        entityDto.setDireccion(updatedDireccion); // update the address
        entityDto.setClave(clave); // it is not send from the client and it is mandatory

        EntityExchangeResult<ControllerResult<ClienteDto>> putRequestResult = webTestClient.put().uri("/api/clientes/" + entityDto.getId())
                .body(Mono.just(entityDto), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {})
                .returnResult();

        ControllerResult<ClienteDto> putResponseBody = putRequestResult.getResponseBody();
        assertThat(putResponseBody).isNotNull();
        assertThat(putResponseBody.getData()).isNotNull();
        assertThat(putResponseBody.getData().getDireccion()).isEqualTo(updatedDireccion);

    }

    @Test
    void patch() {
        final String identificacion = UUID.randomUUID().toString(); // must be unique
        final String clienteId = UUID.randomUUID().toString(); // must be unique
        final String nombre = UUID.randomUUID().toString(); // must be unique

        final String originalEstado = "TRUE";
        final String updatedEstado = "FALSE";


        ClienteDto requestBody = ClienteDto
                .builder()
                .identificacion(identificacion)
                .clienteId(clienteId)
                .nombre(nombre)
                .direccion("Calle 123 N0. 456 y Calle 789")
                .telefono("555-555")
                .clave("Password1234")
                .estado(originalEstado)
                .build();

        EntityExchangeResult<ControllerResult<ClienteDto>> postRequestResult = webTestClient.post().uri("/api/clientes")
                .body(Mono.just(requestBody), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {})
                .returnResult();

        ControllerResult<ClienteDto> postResponseBody = postRequestResult.getResponseBody();
        assertThat(postResponseBody).isNotNull();
        assertThat(postResponseBody.getData()).isNotNull();
        assertThat(postResponseBody.getData().getEstado()).isEqualTo(originalEstado);

        ClienteDto entityDto = postResponseBody.getData();
        ClienteDto patchRequestBody = ClienteDto.builder()
                .estado(updatedEstado)
                .build();

        EntityExchangeResult<ControllerResult<ClienteDto>> putRequestResult = webTestClient.patch().uri("/api/clientes/" + entityDto.getId())
                .body(Mono.just(patchRequestBody), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {})
                .returnResult();

        ControllerResult<ClienteDto> putResponseBody = putRequestResult.getResponseBody();
        assertThat(putResponseBody).isNotNull();
        assertThat(putResponseBody.getData()).isNotNull();
        assertThat(putResponseBody.getData().getEstado()).isEqualTo(updatedEstado);
    }

    @Test
    void delete() {
        String identificacion = UUID.randomUUID().toString(); // must be unique
        String clienteId = UUID.randomUUID().toString(); // must be unique
        String nombre = UUID.randomUUID().toString(); // must be unique


        ClienteDto postRequestBody = ClienteDto
                .builder()
                .identificacion(identificacion)
                .clienteId(clienteId)
                .nombre(nombre)
                .direccion("Calle 123 N0. 456 y Calle 789")
                .telefono("555-555")
                .clave("Password1234")
                .estado("TRUE")
                .build();

        EntityExchangeResult<ControllerResult<ClienteDto>> postRequestResult = webTestClient.post().uri("/api/clientes")
                .body(Mono.just(postRequestBody), ClienteDto.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<ControllerResult<ClienteDto>>() {})
                .returnResult();

        ControllerResult<ClienteDto> postResponseBody = postRequestResult.getResponseBody();
        assertThat(postResponseBody).isNotNull();
        assertThat(postResponseBody.getData()).isNotNull();
        assertThat(postResponseBody.getData().getId()).isNotNull();

        ClienteDto entityDto = postResponseBody.getData();

        EntityExchangeResult<ControllerResult<Void>> putRequestResult = webTestClient.delete().uri("/api/clientes/" + entityDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ControllerResult<Void>>() {})
                .returnResult();

        ControllerResult<Void> putResponseBody = putRequestResult.getResponseBody();
        assertThat(putResponseBody).isNotNull();
        assertThat(putResponseBody.getData()).isNull();
    }
}
