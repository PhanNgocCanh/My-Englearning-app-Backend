package com.exerciseapp.myapp.service;

import com.exerciseapp.myapp.service.dto.LessonDTO;
import java.util.List;

public interface LessonService {
    List<LessonDTO> getAll();
    void create(LessonDTO lessonDTO);
    void delete(String[] lessonId);
}
