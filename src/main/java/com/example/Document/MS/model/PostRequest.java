package com.example.Document.MS.model;

public class PostRequest {
    private Long documentId;
    private String content;

    // Constructors, getters, and setters


    public PostRequest(Long documentId, String content) {
        this.documentId = documentId;
        this.content = content;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
