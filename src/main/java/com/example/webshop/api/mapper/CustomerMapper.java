package com.example.webshop.api.mapper;


import com.example.webshop.api.model.CustomerDTO;
import com.example.webshop.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {OrderMapper.class})
public interface CustomerMapper extends  EntityMapper<Customer, CustomerDTO> {
}
