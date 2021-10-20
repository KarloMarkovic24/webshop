package com.example.webshop.api.mapper;


import com.example.webshop.api.model.OrderItemDTO;
import com.example.webshop.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper extends EntityMapper<OrderItem, OrderItemDTO> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "order.id", target = "orderId")
    OrderItemDTO toDto(OrderItem orderItem);

    @Mapping(source = "productId",target = "product.id")
    @Mapping(source = "orderId", target = "order.id")
    OrderItem toEntity(OrderItemDTO dto);
}
