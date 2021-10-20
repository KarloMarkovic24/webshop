package com.example.webshop.api.mapper;

import com.example.webshop.api.model.OrderDTO;
import com.example.webshop.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {OrderItemMapper.class})
public interface OrderMapper extends EntityMapper<Order,OrderDTO> {

    @Mapping(source = "customer.id",target = "customerId")
    OrderDTO toDto(Order entity);

    @Mapping(source = "customerId", target = "customer.id")
    Order toEntity(OrderDTO dto);
}
