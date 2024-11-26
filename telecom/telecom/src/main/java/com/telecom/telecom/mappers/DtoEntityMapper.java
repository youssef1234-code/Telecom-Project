package com.telecom.telecom.mappers;

import com.telecom.telecom.dtos.CustomerAccountDto;
import com.telecom.telecom.entities.CustomerAccount;
import org.springframework.stereotype.Component;

@Component
public class DtoEntityMapper {

    public CustomerAccountDto toCustomerAccountDto(CustomerAccount entity) {
        if (entity == null) return null;
        return CustomerAccountDto.builder()
                .mobileNo(entity.getMobileNo())
                .balance(entity.getBalance())
                .password(entity.getPass())
                .build();
    }
}
