package ec.dev.samagua.ekumen_bank_clients.services.impl;

import ec.dev.samagua.ekumen_bank_clients.models.Cliente;
import ec.dev.samagua.ekumen_bank_clients.infrastructure.accounts.clients.CuentaServiceClient;
import ec.dev.samagua.ekumen_bank_clients.repositories.impl.ClienteRepository;
import ec.dev.samagua.ekumen_bank_clients.services.ClienteService;
import ec.dev.samagua.ekumen_bank_clients.utils.BeanCopyUtil;
import ec.dev.samagua.commons_lib.utils.EncryptDecryptUtils;
import ec.dev.samagua.commons_lib.exceptions.InvalidDataException;
import ec.dev.samagua.commons_lib.models.DataValidationResult;
import ec.dev.samagua.commons_lib.models.IdentityFieldWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final CuentaServiceClient cuentaServiceClient;

    @Override
    public Mono<Cliente> create(Cliente cliente) {
        Mono<Long> countIdentificacion = repository.countByIdentificacion(cliente.getIdentificacion());
        Mono<Long> countClienteId = repository.countByClienteId(cliente.getClienteId());
        Mono<Long> countNombre = repository.countByNombre(cliente.getNombre());

        return Mono.zip(countIdentificacion, countClienteId, countNombre).flatMap(tuple -> {
                    Long countIdentificacionAsLong = tuple.getT1();
                    Long countClienteIdAsLong = tuple.getT2();
                    Long countNombreAsLong = tuple.getT3();

                    DataValidationResult clientValidationResult = cliente.validateForCreating(countIdentificacionAsLong, countClienteIdAsLong, countNombreAsLong);

                    if (!clientValidationResult.isValid()) {
                        return Mono.error(InvalidDataException.getInstance(clientValidationResult.getErrors()));
                    }

                    String claveAsMD5 = EncryptDecryptUtils.md5(cliente.getClave());
                    cliente.setClave(claveAsMD5);

                    return Mono.empty();
                })
                .then(repository.save(cliente));
    }

    @Override
    public Mono<Cliente> update(Long id, Cliente newData) {
        Mono<Cliente> entityMono = repository.findById(id);
        Mono<Long> countIdentificacionMono = repository.countByIdentificacion(newData.getIdentificacion());
        Mono<Long> countClienteIdMono = repository.countByClienteId(newData.getClienteId());
        Mono<Long> countNombreMono = repository.countByNombre(newData.getNombre());

        return Mono.zip(countIdentificacionMono, countClienteIdMono, entityMono, countNombreMono)
                .flatMap(tuple -> {
                    Cliente entity = tuple.getT3();

                    if (!entity.isValidId()) {
                        return Mono.error(InvalidDataException.getInstance(Collections.singletonMap("id", "is invalid")));
                    }

                    Long countIdentificacion = tuple.getT1();
                    IdentityFieldWrapper identificacionWrapper = new IdentityFieldWrapper(countIdentificacion, Objects.equals(entity.getIdentificacion(), newData.getIdentificacion()));
                    Long countClienteId = tuple.getT2();
                    IdentityFieldWrapper clienteIdWrapper = new IdentityFieldWrapper(countClienteId, Objects.equals(entity.getClienteId(), newData.getClienteId()));
                    Long countNombre = tuple.getT4();
                    IdentityFieldWrapper nombreWrapper = new IdentityFieldWrapper(countNombre, Objects.equals(entity.getNombre(), newData.getNombre()));

                    DataValidationResult clientValidationResult = newData.validateForUpdating(identificacionWrapper, clienteIdWrapper, nombreWrapper);

                    if (!clientValidationResult.isValid()) {
                        return Mono.error(InvalidDataException.getInstance(clientValidationResult.getErrors()));
                    }

                    String claveAsMD5 = EncryptDecryptUtils.md5(newData.getClave());
                    newData.setClave(claveAsMD5);
                    newData.setId(id);

                    BeanUtils.copyProperties(newData, entity);

                    return repository.update(entity);
                });
    }

    @Override
    public Mono<Cliente> patch(Long id, Cliente newData) {
        Mono<Cliente> entityMono = repository.findById(id);
        Mono<Long> countIdentificacionMono = repository.countByIdentificacion(newData.getIdentificacion());
        Mono<Long> countClienteIdMono = repository.countByClienteId(newData.getClienteId());
        Mono<Long> countNombreMono = repository.countByNombre(newData.getNombre());

        return Mono.zip(countIdentificacionMono, countClienteIdMono, entityMono, countNombreMono)
                .flatMap(tuple -> {
                    Cliente entity = tuple.getT3();

                    if (!entity.isValidId()) {
                        return Mono.error(InvalidDataException.getInstance(Collections.singletonMap("id", "is invalid")));
                    }

                    Long countIdentificacion = tuple.getT1();
                    IdentityFieldWrapper identificacionWrapper = new IdentityFieldWrapper(countIdentificacion, Objects.equals(entity.getIdentificacion(), newData.getIdentificacion()));
                    Long countClienteId = tuple.getT2();
                    IdentityFieldWrapper clienteIdWrapper = new IdentityFieldWrapper(countClienteId, Objects.equals(entity.getClienteId(), newData.getClienteId()));
                    Long countNombre = tuple.getT4();
                    IdentityFieldWrapper nombreWrapper = new IdentityFieldWrapper(countNombre, Objects.equals(entity.getNombre(), newData.getNombre()));

                    DataValidationResult clientValidationResult = newData.validateForPatching(identificacionWrapper, clienteIdWrapper, nombreWrapper);

                    if (!clientValidationResult.isValid()) {
                        return Mono.error(InvalidDataException.getInstance(clientValidationResult.getErrors()));
                    }

                    if (newData.getClave() != null) {
                        String claveAsMD5 = EncryptDecryptUtils.md5(newData.getClave());
                        newData.setClave(claveAsMD5);
                    }

                    newData.setId(id);

                    BeanCopyUtil.copyNonNullProperties(newData, entity);

                    return repository.update(entity);
                });
    }

    @Override
    public Mono<Void> delete(Long id) {
        AtomicReference<Cliente> atomicEntity = new AtomicReference<>();

        return repository.findById(id).flatMap(entity -> {
            if (!entity.isValidId()) {
                return Mono.error(InvalidDataException.getInstance(Collections.singletonMap("id", "is invalid")));
            }

            atomicEntity.set(entity);

            return cuentaServiceClient.findByClienteId(entity.getClienteId());
        }).flatMap(cuentas -> {
            if (!cuentas.isEmpty()) {
                return Mono.error(InvalidDataException.getInstance(Collections.singletonMap("cliente", "has accounts associated")));
            }

            return repository.delete(atomicEntity.get());
        });
    }

    @Override
    public Mono<List<Cliente>> search(String nombre, String clienteId) {
        if (nombre == null && clienteId == null) {
            return repository.findAll();
        }
        return repository.findByNombreAndClienteId(nombre, clienteId);
    }
}
