package ec.com.tcs.bank_services.mapper;

import ec.com.tcs.bank_services.dto.request.MovementRequest;
import ec.com.tcs.bank_services.dto.response.MovementReportResponse;
import ec.com.tcs.bank_services.dto.response.MovementResponse;
import ec.com.tcs.bank_services.model.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "account", ignore = true)
    MovementEntity toEntity(MovementRequest request);

    MovementResponse toResponse(MovementEntity entity);

}
