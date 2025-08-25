package ec.com.tcs.bank_services.mapper;

import ec.com.tcs.bank_services.dto.request.ClientRequest;
import ec.com.tcs.bank_services.dto.response.ClientResponse;
import ec.com.tcs.bank_services.model.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "status", constant = "true")
    ClientEntity toEntity(ClientRequest request);

    ClientResponse toResponse(ClientEntity entity);
}
