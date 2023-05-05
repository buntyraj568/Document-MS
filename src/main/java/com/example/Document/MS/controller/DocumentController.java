package com.example.Document.MS.controller;

import com.example.Document.MS.model.Document;
import com.example.Document.MS.repo.CommentRepository;
import com.example.Document.MS.repo.DocumentRepository;
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

    public DocumentController(DocumentRepository documentRepository, CommentRepository commentRepository) {
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
    }

    // Upload a document
    @PostMapping("/upload")
    public Document uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
        // Process and store the uploaded file (PDF) using PDFBox
        String filename = file.getOriginalFilename();

        // Save the document to the database
        Document document = new Document();
        document.setFilename(filename);
        return documentRepository.save(document);
    }

    // Get all documents
    @GetMapping("/getAll")
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // Get a document by ID
    @GetMapping("/{id}")
    public Optional<Document> getDocumentById(@PathVariable("id") Long id) {
        return documentRepository.findById(id);
    }

    // Update a document by ID
    @PutMapping("/{id}")
    public Document updateDocument(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) throws IOException {
        // Process and store the uploaded file (PDF) using PDFBox
        String filename = file.getOriginalFilename();

        // Get the document by ID
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            document.setFilename(filename);
            return documentRepository.save(document);
        } else {
            throw new RuntimeException("Document not found");
        }
    }

    // Delete a document by ID
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        documentRepository.deleteById(id);
    }

    // Add additional methods as needed
}
