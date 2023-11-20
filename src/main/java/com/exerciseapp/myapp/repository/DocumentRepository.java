package com.exerciseapp.myapp.repository;

import com.exerciseapp.myapp.domain.Document;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<Document, String> {
    @Query(
        value = "with recursive generation as(" +
        "select d.document_id, d.parent_id, d.document_name, " +
        "d.document_type, d.path, d.color, d.last_viewed_page " +
        "from Document d " +
        "where d.parent_id  is null and d.lesson_id = :lessonId " +
        "union all " +
        "select child.document_id, child.parent_id, child.document_name, " +
        "child.document_type, child.path, child.color, child.last_viewed_page " +
        "from Document child " +
        "join generation g on child.parent_id = g.document_id" +
        ") " +
        "select * from generation",
        nativeQuery = true
    )
    List<Object[]> getTreeDocument(@Param("lessonId") String lessonId);

    List<Document> getAllByParentId(String parenId);

    @Modifying
    @Query(value = "delete from Document d where d.documentId = :documentId or d.parentId = :documentId")
    void deleteDocumentByDocumentId(@Param("documentId") String documentId);
}
