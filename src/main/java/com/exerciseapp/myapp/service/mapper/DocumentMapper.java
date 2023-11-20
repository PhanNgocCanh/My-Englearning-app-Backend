package com.exerciseapp.myapp.service.mapper;

import com.exerciseapp.myapp.domain.Document;
import com.exerciseapp.myapp.service.dto.DocumentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {}
