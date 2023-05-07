package com.example.Document.MS.service;

import com.example.Document.MS.model.*;
import com.example.Document.MS.repo.CommentRepository;
import com.example.Document.MS.repo.DocumentRepository;
import com.example.Document.MS.repo.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private PostService postService;

    private Post post;
    private Document document;

    @BeforeEach
    public void setUp() {
        document = new Document(1L, "test_document");
        post = new Post(1L, "test_content", document);
    }

    @Test
    public void testCreatePost_Success() {
        // Set up test data
        Document document = new Document();
        document.setId(1L);
        document.setFilename("test_document");

        Post post = new Post();
        post.setId(1L);
        post.setContent("test_content");
        post.setDocument(document);

        PostRequest request = new PostRequest();
        request.setDocumentId(1L);
        request.setContent("test_content");

        // Mock dependencies
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document));
        when(postRepository.save(any())).thenReturn(post);

        // Call method being tested
        Post result = postService.createPost(request);

        // Verify result
        assertNotNull(result);
        assertEquals(post, result);

        // Verify mock interactions
        verify(documentRepository, times(1)).findById(anyLong());
        verify(postRepository, times(1)).save(any());
    }

    @Test
    public void testCreatePost_InvalidDocumentId() {
        PostRequest request = new PostRequest();
        request.setDocumentId(2L);
        request.setContent("test_content");

        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            postService.createPost(request);
        });
    }

    @Test
    public void testCreatePost_Exception() {
        PostRequest request = new PostRequest();
        request.setDocumentId(1L);
        request.setContent("test_content");

        when(documentRepository.findById(anyLong())).thenThrow(new RuntimeException("test exception"));

        assertThrows(RuntimeException.class, () -> {
            postService.createPost(request);
        });
    }

  /*  @Test
    public void testGetPostsByDocument_Success() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(document));
        when(postRepository.findByDocument(any())).thenReturn(List.of(post));

        List<Post> result = postService.getPostsByDocument(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post, result.get(0));
    }*/

    @Test
    public void testGetPostsByDocument_InvalidDocumentId() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            postService.getPostsByDocument(2L);
        });
    }

    @Test
    public void testGetPostsByDocument_Exception() {
        when(documentRepository.findById(anyLong())).thenThrow(new RuntimeException("test exception"));

        assertThrows(RuntimeException.class, () -> {
            postService.getPostsByDocument(1L);
        });
    }

}