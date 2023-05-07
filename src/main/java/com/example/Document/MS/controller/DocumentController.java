package com.example.Document.MS.controller;

import com.example.Document.MS.model.Document;
import com.example.Document.MS.repo.CommentRepository;
import com.example.Document.MS.repo.DocumentRepository;
import com.example.Document.MS.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private final DocumentRepository documentRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final DocumentService documentService;

    public DocumentController(DocumentRepository documentRepository, CommentRepository commentRepository, DocumentService documentService) {
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public Document uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
        return documentService.uploadDocument(file);
    }


    // Get all documents
    @GetMapping("/getAll")
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public Optional<Document> getDocumentById(@PathVariable("id") Long id) {
        return documentService.getDocumentById(id);
    }

    // Update a document by ID
    @PutMapping("/{id}")
    public Document updateDocument(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        return documentService.updateDocument(id, file);
    }

    // Delete a document by ID
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);
    }


}
