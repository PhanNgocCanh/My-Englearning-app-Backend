package com.exerciseapp.myapp.service;

import com.exerciseapp.myapp.service.dto.CollectionDTO;
import com.exerciseapp.myapp.service.request.SearchCollectionRequest;
import com.exerciseapp.myapp.service.response.CollectionGetAllResponse;
import com.exerciseapp.myapp.service.response.PageDataResponse;
import org.springframework.data.domain.Page;

public interface CollectionService {
    PageDataResponse<CollectionGetAllResponse> getAll(SearchCollectionRequest request);

    CollectionDTO create(CollectionDTO collectionDTO);

    void delete(String collectionId);
}
