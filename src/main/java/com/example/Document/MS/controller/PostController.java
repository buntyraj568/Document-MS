package com.example.Document.MS.controller;

import com.example.Document.MS.model.*;
import com.example.Document.MS.repo.CommentRepository;
import com.example.Document.MS.repo.DocumentRepository;
import com.example.Document.MS.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController

public class PostController {
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final DocumentRepository documentRepository;
    @Autowired
    private final CommentRepository commentRepository;

    public PostController(PostRepository postRepository, DocumentRepository documentRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        Post post = new Post();
        post.setContent(request.getContent());
        post.setDocument(document);

        Post createdPost = postRepository.save(post);

        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<List<Post>> getPostsByDocument(@PathVariable("id") Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        List<Post> posts = postRepository.findByDocument(document);

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable("postId") Long postId, @RequestBody CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        ConcreteComment comment = new ConcreteComment();
        comment.setContent(request.getContent());
        comment.setPost(post);

        Comment createdComment = commentRepository.save(comment);

        return ResponseEntity.ok(createdComment);
    }

    // Add additional methods as needed

    // Example: Get all posts
    @GetMapping("/getPost")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return ResponseEntity.ok(posts);
    }
}