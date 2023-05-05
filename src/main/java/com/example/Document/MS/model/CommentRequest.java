package com.example.Document.MS.model;

public class CommentRequest {
    private Long postId;
    private String content;

    // Constructors, getters, and setters

    public CommentRequest(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
