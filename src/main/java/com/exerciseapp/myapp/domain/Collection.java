package com.exerciseapp.myapp.domain;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "collection")
public class Collection extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "collection_id")
    private String collectionId;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "description")
    private String description;

    @Column(name = "user_manager")
    private String userManager;

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getUserManager() {
        return userManager;
    }

    public void setUserManager(String userManager) {
        this.userManager = userManager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
