package com.example.Document.MS.service;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long postId) {
        super("Post not found with ID: " + postId);
    }
}
