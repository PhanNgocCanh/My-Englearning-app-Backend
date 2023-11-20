package com.exerciseapp.myapp.web.rest;

import com.exerciseapp.myapp.service.CollectionService;
import com.exerciseapp.myapp.service.dto.CollectionDTO;
import com.exerciseapp.myapp.service.request.SearchCollectionRequest;
import com.exerciseapp.myapp.service.response.ApiResponse;
import com.exerciseapp.myapp.service.response.CollectionGetAllResponse;
import com.exerciseapp.myapp.service.response.PageDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collection")
public class CollectionController {

    private final Logger log = LoggerFactory.getLogger(CollectionController.class);

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/get-all")
    public ResponseEntity<ApiResponse<PageDataResponse<CollectionGetAllResponse>>> getAll(@RequestBody SearchCollectionRequest request) {
        PageDataResponse<CollectionGetAllResponse> response = this.collectionService.getAll(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CollectionDTO>> create(@RequestBody CollectionDTO collectionDTO) {
        CollectionDTO response = this.collectionService.create(collectionDTO);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam("collectionId") String collectionId) {
        this.collectionService.delete(collectionId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
