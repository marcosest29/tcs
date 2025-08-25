package ec.com.tcs.bank_services.mapper;

import ec.com.tcs.bank_services.dto.request.AccountRequest;
import ec.com.tcs.bank_services.dto.response.AccountResponse;
import ec.com.tcs.bank_services.model.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "status", constant = "true")
    AccountEntity toEntity(AccountRequest request);

    AccountResponse toResponse(AccountEntity entity);
}
