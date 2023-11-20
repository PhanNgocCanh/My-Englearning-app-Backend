package com.exerciseapp.myapp.service.mapper;

import com.exerciseapp.myapp.domain.Collection;
import com.exerciseapp.myapp.service.dto.CollectionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollectionMapper extends EntityMapper<CollectionDTO, Collection> {}
