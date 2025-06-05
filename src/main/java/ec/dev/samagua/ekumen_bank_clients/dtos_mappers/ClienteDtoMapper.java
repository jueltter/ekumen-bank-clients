package ec.dev.samagua.ekumen_bank_clients.dtos_mappers;

import ec.dev.samagua.ekumen_bank_clients.dtos.ClienteDto;
import ec.dev.samagua.ekumen_bank_clients.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteDtoMapper {
    //@Mapping(source = "fechaNacimiento", target = "fechaNacimiento", dateFormat = "dd/MM/yyyy")
    ClienteDto entityToDto(Cliente cliente);

    //@Mapping(source = "fechaNacimiento", target = "fechaNacimiento", dateFormat = "dd/MM/yyyy")
    Cliente dtoToEntity(ClienteDto clienteDto);

    @Mapping(ignore = true, target = "clave")
    ClienteDto entityToDtoObfuscated(Cliente cliente);
}
