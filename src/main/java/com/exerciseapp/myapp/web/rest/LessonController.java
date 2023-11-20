package com.exerciseapp.myapp.web.rest;

import com.exerciseapp.myapp.service.LessonService;
import com.exerciseapp.myapp.service.dto.LessonDTO;
import com.exerciseapp.myapp.service.response.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<LessonDTO>>> getAll() {
        List<LessonDTO> response = this.lessonService.getAll();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> create(@RequestBody LessonDTO lessonDTO) {
        this.lessonService.create(lessonDTO);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody String[] lessonIds) {
        this.lessonService.delete(lessonIds);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
