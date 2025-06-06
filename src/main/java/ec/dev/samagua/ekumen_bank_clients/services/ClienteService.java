package ec.dev.samagua.ekumen_bank_clients.services;

import ec.dev.samagua.ekumen_bank_clients.models.Cliente;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClienteService {
    Mono<Cliente> create(Cliente cliente);
    Mono<Cliente> update(Long id, Cliente newData);
    Mono<Cliente> patch(Long id, Cliente newData);
    Mono<Void> delete(Long id);
    Mono<List<Cliente>> search(String nombre, String clienteId);
}
