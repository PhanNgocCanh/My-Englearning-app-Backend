package com.exerciseapp.myapp.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NodeDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String documentNodeId;
    private String parentId;
    private String documentNodeName;
    private String documentNodeType;
    private String path;
    private String color;
    private int lastViewedPage;
    private List<NodeDataDTO> children = new ArrayList<>();

    public NodeDataDTO() {}

    public NodeDataDTO(
        String documentNodeId,
        String parentId,
        String documentNodeName,
        String documentNodeType,
        String path,
        String color,
        int lastViewedPage
    ) {
        this.documentNodeId = documentNodeId;
        this.parentId = parentId;
        this.documentNodeName = documentNodeName;
        this.documentNodeType = documentNodeType;
        this.path = path;
        this.color = color;
        this.lastViewedPage = lastViewedPage;
    }

    public String getDocumentNodeId() {
        return documentNodeId;
    }

    public void setDocumentNodeId(String documentNodeId) {
        this.documentNodeId = documentNodeId;
    }

    public String getDocumentNodeName() {
        return documentNodeName;
    }

    public void setDocumentNodeName(String documentNodeName) {
        this.documentNodeName = documentNodeName;
    }

    public String getDocumentNodeType() {
        return documentNodeType;
    }

    public void setDocumentNodeType(String documentNodeType) {
        this.documentNodeType = documentNodeType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<NodeDataDTO> getChildren() {
        return children;
    }

    public void addChildren(NodeDataDTO children) {
        this.children.add(children);
    }

    public void setChildren(List<NodeDataDTO> children) {
        this.children = children;
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
}
