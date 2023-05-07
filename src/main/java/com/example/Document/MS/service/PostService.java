package com.example.Document.MS.service;
import com.example.Document.MS.model.*;
import com.example.Document.MS.repo.CommentRepository;
import com.example.Document.MS.repo.DocumentRepository;
import com.example.Document.MS.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final DocumentRepository documentRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostService(PostRepository postRepository, DocumentRepository documentRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
    }

    public Post createPost(PostRequest request) {
        try {
            Document document = documentRepository.findById(request.getDocumentId())
                    .orElseThrow(() -> new DocumentNotFoundException(request.getDocumentId()));

            Post post = new Post();
            post.setContent(request.getContent());
            post.setDocument(document);

            return postRepository.save(post);
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create post: " + e.getMessage());
        }
    }

    public List<Post> getPostsByDocument(Long documentId) {
        try {
            Document document = documentRepository.findById(documentId)
                    .orElseThrow(() -> new DocumentNotFoundException(documentId));

            return postRepository.findByDocument(document);
        } catch (DocumentNotFoundException e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get posts: " + e.getMessage());
        }
    }

    public Comment createComment(Long postId, CommentRequest request) {
        try {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new PostNotFoundException(postId));

            ConcreteComment comment = new ConcreteComment();
            comment.setContent(request.getContent());
            comment.setPost(post);

            return commentRepository.save(comment);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create comment: " + e.getMessage());
        }
    }

    // Add additional methods as needed
}
