package com.exerciseapp.myapp.service.impl;

import com.exerciseapp.myapp.common.constants.ErrorConstant;
import com.exerciseapp.myapp.common.exception.CommonException;
import com.exerciseapp.myapp.domain.Lesson;
import com.exerciseapp.myapp.repository.LessonRepository;
import com.exerciseapp.myapp.service.LessonService;
import com.exerciseapp.myapp.service.dto.LessonDTO;
import com.exerciseapp.myapp.service.mapper.LessonMapper;
import com.exerciseapp.myapp.utils.system.SecurityContextHolder;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonMapper lessonMapper, LessonRepository lessonRepository) {
        this.lessonMapper = lessonMapper;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<LessonDTO> getAll() {
        try {
            String userId = SecurityContextHolder.getCurrentUserId();
            List<Lesson> lessons = lessonRepository.findAll(userId);

            return lessonMapper.toDTO(lessons);
        } catch (Exception e) {
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstant.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void create(LessonDTO lessonDTO) {
        try {
            String userId = SecurityContextHolder.getCurrentUserId();
            if (StringUtils.isEmpty(lessonDTO.getLessonName())) {
                throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstant.LESSON_NAME_EMPTY);
            }
            Lesson lesson = lessonMapper.toEntity(lessonDTO);
            lesson.setUserManager(userId);
            lessonRepository.save(lesson);
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void delete(String[] lessonId) {
        try {
            for (String id : lessonId) {
                Optional<Lesson> lessonOpt = lessonRepository.findById(id);
                if (lessonOpt.isEmpty()) {
                    throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.LESSON_NOT_EXISTS);
                }
                lessonRepository.deleteById(id);
            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstant.INTERNAL_SERVER_ERROR);
        }
    }
}
