package com.example.webshop.api.mapper;


import java.util.Set;

public interface EntityMapper<E,D> {
    E toEntity(D dto);

    D toDto(E entity);

    Set <E> toEntity(Set<D> dtoList);

    Set<D> toDto(Set<E> entityList);
}
