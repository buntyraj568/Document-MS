package com.example.Document.MS.controller;

import com.example.Document.MS.model.Comment;
import com.example.Document.MS.model.CommentRequest;
import com.example.Document.MS.model.ConcreteComment;
import com.example.Document.MS.model.Document;
import com.example.Document.MS.model.Post;
import com.example.Document.MS.model.PostRequest;
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
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;
    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostController(PostRepository postRepository, DocumentRepository documentRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
    }

    // Create a new post
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        // Find the associated document
        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        // Create a new post entity
        Post post = new Post();
        post.setContent(request.getContent());
        post.setDocument(document);

        // Save the post in the repository
        Post createdPost = postRepository.save(post);

        // Return the created post in the response
        return ResponseEntity.ok(createdPost);
    }

    // Get posts by document
    @GetMapping("/document/{id}")
    public ResponseEntity<List<Post>> getPostsByDocument(@PathVariable("id") Long documentId) {
        // Find the document by ID
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        // Find the posts associated with the document
        List<Post> posts = postRepository.findByDocument(document);

        // Return the posts in the response
        return ResponseEntity.ok(posts);
    }

    // Create a new comment for a post
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable("postId") Long postId, @RequestBody CommentRequest request) {
        // Find the post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        // Create a new comment entity
        ConcreteComment comment = new ConcreteComment();
        comment.setContent(request.getContent());
        comment.setPost(post);

        // Save the comment in the repository
        Comment createdComment = commentRepository.save(comment);

        // Return the created comment in the response
        return ResponseEntity.ok(createdComment);
    }

    // Get all posts
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        // Retrieve all posts from the repository
        List<Post> posts = postRepository.findAll();

        // Return the posts in the response
        return ResponseEntity.ok(posts);
    }
}
