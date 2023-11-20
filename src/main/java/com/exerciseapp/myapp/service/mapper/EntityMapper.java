package com.exerciseapp.myapp.service.mapper;

import java.util.List;

/**
 *
 * @param <D> - DTO type parameter
 * @param <E> - Entity type parameter
 */

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDTO(E entity);

    List<E> toEntity(List<D> dto);

    List<D> toDTO(List<E> entity);
}
