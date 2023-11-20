package com.exerciseapp.myapp.service.mapper;

import com.exerciseapp.myapp.domain.Lesson;
import com.exerciseapp.myapp.service.dto.LessonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonMapper extends EntityMapper<LessonDTO, Lesson> {}
