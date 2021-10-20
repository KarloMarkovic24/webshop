package com.example.webshop.api.mapper;

import com.example.webshop.api.model.ProductDTO;
import com.example.webshop.domain.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<Product, ProductDTO> {

    @Mapping(source = "orderItem.id", target = "orderItemId")
    ProductDTO toDto(Product product);

    @Mapping(source = "orderItemId", target = "orderItem.id")
    Product toEntity(ProductDTO dto);
}
