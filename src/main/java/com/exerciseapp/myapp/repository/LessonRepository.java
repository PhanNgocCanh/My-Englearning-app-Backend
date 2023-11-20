package com.exerciseapp.myapp.repository;

import com.exerciseapp.myapp.domain.Lesson;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    @Query("select l from Lesson  l where l.userManager = :userManager order by l.createdDate desc ")
    List<Lesson> findAll(@Param("userManager") String userId);
}
