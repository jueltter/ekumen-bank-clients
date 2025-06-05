package ec.dev.samagua.ekumen_bank_clients.repositories;

import ec.dev.samagua.ekumen_bank_clients.entities.Cliente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteReactiveRepository extends ReactiveCrudRepository<Cliente, Long> {

    Flux<Cliente> findByNombreOrClienteId(String nombre, String clienteId);

    Mono<Long> countByClienteId(String clienteId);

    Mono<Long> countByIdentificacion(String identificacion);

    Mono<Long> countByNombre(String nombre);

}
