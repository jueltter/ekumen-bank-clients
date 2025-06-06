package ec.dev.samagua.ekumen_bank_clients.controllers;

import ec.dev.samagua.ekumen_bank_clients.dtos.ClienteDto;
import ec.dev.samagua.ekumen_bank_clients.dtos.mappers.ClienteDtoMapper;
import ec.dev.samagua.ekumen_bank_clients.models.Cliente;
import ec.dev.samagua.ekumen_bank_clients.services.ClienteService;
import ec.dev.samagua.commons_lib.models.controllers.ControllerResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ClienteController {

    private final ClienteService service;
    private final ClienteDtoMapper mapper; // ignore

    @GetMapping("/clientes")
    public Mono<ResponseEntity<ControllerResult<List<ClienteDto>>>> search(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "cliente-id", required = false) String clienteId
    ) {
        log.debug("executing GET /clientes, nombre: {}, clienteId: {}", nombre, clienteId);
        Mono<List<Cliente>> entities = service.search(nombre, clienteId);
        return entities.map(obj -> {
            ControllerResult<List<ClienteDto>> body = ControllerResult.getSuccessResult(obj.stream()
                    .map(mapper::entityToDtoObfuscated)
                    .toList());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(body);
        }).doOnSuccess(obj -> log.debug("GET /clientes response: {}", obj));
    }

    @PostMapping("/clientes")
    public Mono<ResponseEntity<ControllerResult<ClienteDto>>> create(@RequestBody ClienteDto dto) {
        Cliente entity = mapper.dtoToEntity(dto);
        log.debug("executing POST /clientes, body: {}", dto);
        log.debug("executing POST /clientes, bodyToEntity: {}", entity);

        Mono<Cliente> entityAsMono = service.create(entity);
        return entityAsMono.map(obj -> {
            ControllerResult<ClienteDto> body = ControllerResult.getSuccessResult(mapper.entityToDtoObfuscated(obj));
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(body);
        }).doOnSuccess(obj -> log.debug("POST /clientes response: {}", obj));
    }

    @PutMapping("/clientes/{id}")
    public Mono<ResponseEntity<ControllerResult<ClienteDto>>> update(@PathVariable Long id, @RequestBody ClienteDto dto) {
        Cliente entity = mapper.dtoToEntity(dto);
        log.debug("executing PUT /clientes, body: {}", dto);
        log.debug("executing PUT /clientes, bodyToEntity: {}", entity);

        Mono<Cliente> entityAsMono = service.update(id, entity);
        return entityAsMono.map(obj -> {
            ControllerResult<ClienteDto> body = ControllerResult.getSuccessResult(mapper.entityToDtoObfuscated(obj));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(body);
        }).doOnSuccess(obj -> log.debug("PUT /clientes response: {}", obj));
    }

    @PatchMapping("/clientes/{id}")
    public Mono<ResponseEntity<ControllerResult<ClienteDto>>> patch(@PathVariable Long id, @RequestBody ClienteDto dto) {
        Cliente entity = mapper.dtoToEntity(dto);
        log.debug("executing PATCH /clientes, body: {}", dto);
        log.debug("executing PATCH /clientes, bodyToEntity: {}", entity);

        Mono<Cliente> entityAsMono = service.patch(id, entity);
        return entityAsMono.map(obj -> {
            ControllerResult<ClienteDto> body = ControllerResult.getSuccessResult(mapper.entityToDtoObfuscated(obj));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(body);
        }).doOnSuccess(obj -> log.debug("PATCH /clientes response: {}", obj));
    }

    @DeleteMapping("/clientes/{id}")
    public Mono<ResponseEntity<ControllerResult<Void>>> delete(@PathVariable Long id) {
        log.debug("executing DELETE /clientes, id: {}", id);

        Mono<Void> voidMono = service.delete(id);

        ControllerResult<Void> body = ControllerResult.getSuccessResult();
        ResponseEntity<ControllerResult<Void>> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(body);

        return voidMono.then(Mono.just(response)).doOnSuccess(obj -> log.debug("DELETE /clientes response: {}", obj));
    }


}
