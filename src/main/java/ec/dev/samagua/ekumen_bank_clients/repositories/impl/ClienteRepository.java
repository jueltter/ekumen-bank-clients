package ec.dev.samagua.ekumen_bank_clients.repositories.impl;

import ec.dev.samagua.ekumen_bank_clients.models.Cliente;
import ec.dev.samagua.commons_lib.exceptions.RepositoryException;
import ec.dev.samagua.ekumen_bank_clients.repositories.ClienteReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ClienteRepository {

    private final ClienteReactiveRepository repository;

    public Mono<Long> countByClienteId(String clienteId) {
        return repository.countByClienteId(clienteId)
                .onErrorMap(RepositoryException::getReadException)
                .doOnError(error -> log.error("Error counting clients by client ID", error));
    }

    public Mono<Long> countByIdentificacion(String identificacion) {
        return repository.countByIdentificacion(identificacion)
                .onErrorMap(RepositoryException::getReadException)
                .doOnError(error -> log.error("Error counting clients by identification", error));
    }

    public Mono<Long> countByNombre(String nombre) {
        return repository.countByNombre(nombre)
                .onErrorMap(RepositoryException::getReadException)
                .doOnError(error -> log.error("Error counting clients by name", error));
    }

    public Mono<Cliente> save(Cliente cliente) {
        return repository.save(cliente)
                .onErrorMap(RepositoryException::getCreateException)
                .doOnError(error -> log.error("Error creating client", error));
    }

    public Mono<Cliente> update(Cliente cliente) {
        return repository.save(cliente)
                .onErrorMap(RepositoryException::getUpdateException)
                .doOnError(error -> log.error("Error updating client", error));
    }

    public Mono<Void> delete(Cliente cliente) {
        return repository.delete(cliente)
                .onErrorMap(RepositoryException::getDeleteException)
                .doOnError(error -> log.error("Error deleting client", error));
    }

    public Mono<Cliente> findById(Long id) {
        return repository.findById(id)
                .onErrorMap(RepositoryException::getReadException)
                .doOnError(error -> log.error("Error finding client", error))
                .defaultIfEmpty(Cliente.getDefaultInstance());
    }

    public Mono<List<Cliente>> findAll() {
        return repository.findAll()
                .onErrorMap(RepositoryException::getReadException)
                .doOnError(error -> log.error("Error finding all clients", error))
                .collectList();
    }

    public Mono<List<Cliente>> findByNombreAndClienteId(String clienteId, String nombre) {
        return repository.findByNombreOrClienteId(clienteId, nombre)
                .onErrorMap(RepositoryException::getReadException)
                .doOnError(error -> log.error("Error finding client by client ID", error))
                .collectList();
    }
}
