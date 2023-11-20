package com.exerciseapp.myapp.domain;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "document")
public class Document extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "document_id")
    private String documentId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "path")
    private String path;

    @Column(name = "color")
    private String color;

    @Column(name = "last_viewed_page")
    private int lastViewedPage;

    @Column(name = "lesson_id")
    private String lessonId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLastViewedPage() {
        return lastViewedPage;
    }

    public void setLastViewedPage(int lastViewedPage) {
        this.lastViewedPage = lastViewedPage;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
}
