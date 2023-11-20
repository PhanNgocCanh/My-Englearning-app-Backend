package com.exerciseapp.myapp.domain;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lesson")
public class Lesson extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "lesson_id")
    private String lessonId;

    @Column(name = "lesson_name")
    private String lessonName;

    @Column(name = "description")
    private String description;

    @Column(name = "user_manager")
    private String userManager;

    @Column(name = "collection_id")
    private String collectionId;

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserManager() {
        return userManager;
    }

    public void setUserManager(String userManager) {
        this.userManager = userManager;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
