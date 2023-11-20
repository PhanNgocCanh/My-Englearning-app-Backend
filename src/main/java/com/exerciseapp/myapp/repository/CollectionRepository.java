package com.exerciseapp.myapp.repository;

import com.exerciseapp.myapp.domain.Collection;
import com.exerciseapp.myapp.service.request.SearchCollectionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionRepository extends JpaRepository<Collection, String> {
    @Query(
        value = "select c.collection_id, c.collection_name, c.description, coalesce(total,0), c.created_date " +
        "from Collection c left join (select l.collection_id, COUNT(l.collection_id) as total from Lesson l group by l.collection_id) c1 " +
        "on c1.collection_id = c.collection_id " +
        "where unaccent_search(c.collection_name, :#{#request.keyword}) " +
        "and c.user_manager = :userId",
        nativeQuery = true
    )
    Page<Object[]> search(@Param("request") SearchCollectionRequest request, @Param("userId") String userId, Pageable pageable);
}
