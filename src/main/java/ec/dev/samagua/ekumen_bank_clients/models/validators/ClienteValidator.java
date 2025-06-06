package ec.dev.samagua.ekumen_bank_clients.models.validators;

import ec.dev.samagua.commons_lib.models.DataValidationResult;
import ec.dev.samagua.commons_lib.models.IdentityFieldWrapper;
import ec.dev.samagua.ekumen_bank_clients.models.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClienteValidator {
    private static final List<String> GENEROS = List.of("MALE", "FEMALE", "PREFER NOT TO SAY");
    private static final List<String> ESTADOS = List.of("TRUE", "FALSE");

    public DataValidationResult validateForCreating(Cliente model, Long countIdentificacion, Long countClienteId, Long countNombre) {
        Map<String, String> errors = new HashMap<>();

        // validate id
        if (model.getId() != null) {
            errors.put("id", "must be null");
        }

        // validate client name
        if (countNombre > 0) {
            errors.put("nombre", "is already in use");
        }
        else {
            if (model.getNombre() == null || model.getNombre().isBlank()) {
                errors.put("nombre", "is mandatory");
            }
        }

        // validate client gender
        if (model.getGenero() != null) {
            if (!GENEROS.contains(model.getGenero())) {
                errors.put("genero", "possible values are: " + GENEROS);
            }
        }

        // validate client birthdate
        if (model.getFechaNacimiento() != null) {
            if (model.getFechaNacimiento().isAfter(LocalDate.now())) {
                errors.put("fechaNacimiento", "is mandatory and must be a past date");
            }
        }

        // validate client identification
        if (countIdentificacion > 0) {
            errors.put("identificacion", "is already in use");
        }
        else {
            if (model.getIdentificacion() == null || model.getIdentificacion().isBlank()) {
                errors.put("identificacion", "is mandatory");
            }
        }

        // validate address
        if (model.getDireccion() == null || model.getDireccion().isBlank()) {
            errors.put("direccion", "is mandatory");
        }

        // validate phone
        if (model.getTelefono() == null || model.getTelefono().isBlank()) {
            errors.put("telefono", "is mandatory");
        }

        // validate client ID
        if (countClienteId > 0) {
            errors.put("clienteId", "is already in use");
        }
        else {
            if (model.getClienteId() == null || model.getClienteId().isBlank()) {
                errors.put("clienteId", "is mandatory");
            }
        }

        // validate password
        if (model.getClave() == null || model.getClave().isBlank()) {
            errors.put("clave", "is mandatory");
        }

        // validate client status
        if (model.getEstado() == null || !ESTADOS.contains(model.getEstado())) {
            errors.put("estado", "possible values are: " + ESTADOS);
        }

        if (!errors.isEmpty()) {
            return DataValidationResult.builder()
                    .valid(Boolean.FALSE)
                    .errors(errors)
                    .build();

        }

        return DataValidationResult.builder()
                .valid(Boolean.TRUE)
                .errors(null)
                .build();
    }

    public DataValidationResult validateForUpdating(Cliente model, IdentityFieldWrapper identificacionWrapper, IdentityFieldWrapper clienteIdWrapper, IdentityFieldWrapper nombreWrapper) {
        Map<String, String> errors = new HashMap<>();

        // validate client name
        if (!nombreWrapper.noChange() && nombreWrapper.count() > 0) {
            errors.put("nombre", "is already in use");
        }

        // validate client gender
        if (model.getGenero() != null) {
            if (!GENEROS.contains(model.getGenero())) {
                errors.put("genero", "possible values are: " + GENEROS);
            }
        }

        // validate client birthdate
        if (model.getFechaNacimiento() != null) {
            if (model.getFechaNacimiento().isAfter(LocalDate.now())) {
                errors.put("fechaNacimiento", "is mandatory and must be a past date");
            }
        }

        // validate client identification
        if (!identificacionWrapper.noChange() && identificacionWrapper.count() > 0) {
            errors.put("identificacion", "is already in use");
        }

        // validate address
        if (model.getDireccion() == null || model.getDireccion().isBlank()) {
            errors.put("direccion", "is mandatory");
        }

        // validate phone
        if (model.getTelefono() == null || model.getTelefono().isBlank()) {
            errors.put("telefono", "is mandatory");
        }

        // validate client ID
        if (!clienteIdWrapper.noChange() && clienteIdWrapper.count() > 0) {
            errors.put("clienteId", "is already in use");
        }

        // validate password
        if (model.getClave() == null || model.getClave().isBlank()) {
            errors.put("clave", "is mandatory");
        }

        // validate client status
        if (model.getEstado() == null || !ESTADOS.contains(model.getEstado())) {
            errors.put("estado", "possible values are: " + ESTADOS);
        }

        if (!errors.isEmpty()) {
            return DataValidationResult.builder()
                    .valid(Boolean.FALSE)
                    .errors(errors)
                    .build();

        }

        return DataValidationResult.builder()
                .valid(Boolean.TRUE)
                .errors(null)
                .build();
    }

    public DataValidationResult validateForPatching(Cliente model, IdentityFieldWrapper identificacionWrapper, IdentityFieldWrapper clienteIdWrapper, IdentityFieldWrapper nombreWrapper) {
        Map<String, String> errors = new HashMap<>();

        // validate client name
        if (model.getNombre() != null && !nombreWrapper.noChange() && nombreWrapper.count() > 0) {
            errors.put("nombre", "is already in use");
        }

        // validate client gender
        if (model.getGenero() != null && !GENEROS.contains(model.getGenero())) {
            errors.put("genero", "possible values are: " + GENEROS);
        }

        // validate client birthdate
        if (model.getFechaNacimiento() != null && model.getFechaNacimiento().isAfter(LocalDate.now())) {
            errors.put("fechaNacimiento", "is mandatory and must be a past date");
        }

        // validate client identification
        if (model.getIdentificacion() != null && !identificacionWrapper.noChange() && identificacionWrapper.count() > 0) {
            errors.put("identificacion", "is already in use");
        }

        // validate address
        if (model.getDireccion() != null && model.getDireccion().isBlank()) {
            errors.put("direccion", "is mandatory");
        }

        // validate phone
        if (model.getTelefono() != null && model.getTelefono().isBlank()) {
            errors.put("telefono", "is mandatory");
        }

        // validate client ID
        if (model.getClienteId() != null && !clienteIdWrapper.noChange() && clienteIdWrapper.count() > 0) {
            errors.put("clienteId", "is already in use");
        }

        // validate password
        if (model.getClave() != null && model.getClave().isBlank()) {
            errors.put("clave", "is mandatory");
        }

        // validate client status
        if (model.getEstado() != null && !ESTADOS.contains(model.getEstado())) {
            errors.put("estado", "possible values are: " + ESTADOS);
        }

        if (!errors.isEmpty()) {
            return DataValidationResult.builder()
                    .valid(Boolean.FALSE)
                    .errors(errors)
                    .build();

        }

        return DataValidationResult.builder()
                .valid(Boolean.TRUE)
                .errors(null)
                .build();
    }
}
