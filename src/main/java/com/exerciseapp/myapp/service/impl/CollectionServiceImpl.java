package com.exerciseapp.myapp.service.impl;

import com.exerciseapp.myapp.common.constants.ErrorConstant;
import com.exerciseapp.myapp.common.exception.CommonException;
import com.exerciseapp.myapp.domain.Collection;
import com.exerciseapp.myapp.domain.User;
import com.exerciseapp.myapp.repository.CollectionRepository;
import com.exerciseapp.myapp.repository.UserRepository;
import com.exerciseapp.myapp.service.CollectionService;
import com.exerciseapp.myapp.service.dto.CollectionDTO;
import com.exerciseapp.myapp.service.mapper.CollectionMapper;
import com.exerciseapp.myapp.service.request.SearchCollectionRequest;
import com.exerciseapp.myapp.service.response.CollectionGetAllResponse;
import com.exerciseapp.myapp.service.response.PageDataResponse;
import com.exerciseapp.myapp.utils.PageableUtil;
import com.exerciseapp.myapp.utils.StringUtil;
import com.exerciseapp.myapp.utils.system.SecurityContextHolder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    private final UserRepository userRepository;

    private final CollectionMapper collectionMapper;

    public CollectionServiceImpl(
        UserRepository userRepository,
        CollectionRepository collectionRepository,
        CollectionMapper collectionMapper
    ) {
        this.userRepository = userRepository;
        this.collectionRepository = collectionRepository;
        this.collectionMapper = collectionMapper;
    }

    @Override
    public PageDataResponse<CollectionGetAllResponse> getAll(SearchCollectionRequest request) {
        try {
            String userId = SecurityContextHolder.getCurrentUserId();
            validateSearchRequest(request);
            Pageable pageable = PageableUtil.of(request.getPage(), request.getSize(), request.getSorts(), true);
            Page<Object[]> rs = collectionRepository.search(request, userId, pageable);
            List<CollectionGetAllResponse> data = rs
                .toList()
                .stream()
                .map(collection -> {
                    CollectionGetAllResponse response = new CollectionGetAllResponse();
                    response.setCollectionId(collection[0].toString());
                    response.setCollectionName(collection[1].toString());
                    response.setDescription(collection[2] != null ? collection[2].toString() : "");
                    response.setTotalDocuments(Integer.parseInt(collection[3].toString()));
                    response.setCreatedDate(((Timestamp) collection[4]).toInstant());
                    return response;
                })
                .collect(Collectors.toList());

            return PageDataResponse.of(rs.getNumberOfElements(), rs.getNumber(), rs.getSize(), data);
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    private void validateSearchRequest(SearchCollectionRequest request) {
        request.setKeyword(StringUtil.validateKeywordSearchNull(request.getKeyword()));
        if (request.getKeyword() == null) request.setKeyword("");
        request.setCollectionName(StringUtil.validateKeywordSearchNull(request.getCollectionName()));
        if (request.getCollectionName() == null) request.setCollectionName("");
        if (request.getSorts() == null) {
            request.setSorts(new HashMap<>());
        }
    }

    @Override
    public CollectionDTO create(CollectionDTO collectionDTO) {
        try {
            String userId = SecurityContextHolder.getCurrentUserId();

            if (StringUtils.isEmpty(collectionDTO.getCollectionName())) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.COLLECTION_NAME_EMPTY);
            }
            User manageUser = userRepository.findById(userId).orElse(null);
            if (manageUser == null) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.USER_NOT_FOUND);
            }
            if (collectionDTO.getUserId() != null && !userId.equals(collectionDTO.getUserId())) {
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.IS_NOT_MANAGER_USER);
            }

            Collection collection = collectionMapper.toEntity(collectionDTO);
            collection.setUserManager(userId);
            Collection savedCollection = collectionRepository.save(collection);

            return collectionMapper.toDTO(savedCollection);
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }

    @Override
    public void delete(String collectionId) {
        try {
            collectionRepository.deleteById(collectionId);
        } catch (Exception e) {
            throw CommonException
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ErrorConstant.INTERNAL_SERVER_ERROR)
                .message(e.getMessage());
        }
    }
}
