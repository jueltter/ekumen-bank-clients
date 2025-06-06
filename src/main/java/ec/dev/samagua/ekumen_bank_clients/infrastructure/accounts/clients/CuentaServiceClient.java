package ec.dev.samagua.ekumen_bank_clients.infrastructure.accounts.clients;

import ec.dev.samagua.ekumen_bank_clients.infrastructure.accounts.models.Cuenta;
import ec.dev.samagua.commons_lib.models.controllers.ControllerResult;
import ec.dev.samagua.commons_lib.exceptions.RepositoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CuentaServiceClient {
    private final WebClient webClient;

    public Mono<List<Cuenta>> findByClienteId(String clienteId) {
        return webClient.get()
                .uri("/cuentas?cliente-id={clienteId}", clienteId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ControllerResult<List<Cuenta>>>() {})
                .onErrorMap(RepositoryException::getReadException)
                .doOnError(e -> log.error("Error fetching accounts", e))
                .map(ControllerResult::getData);
    }
}
