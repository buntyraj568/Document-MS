package com.example.Document.MS.service;

import com.example.Document.MS.model.Document;
import com.example.Document.MS.repo.CommentRepository;
import com.example.Document.MS.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private final DocumentRepository documentRepository;
    @Autowired
    private final CommentRepository commentRepository;

    public DocumentService(DocumentRepository documentRepository, CommentRepository commentRepository) {
        this.documentRepository = documentRepository;
        this.commentRepository = commentRepository;
    }

    public Document uploadDocument(MultipartFile file) throws IOException {
        // Process and store the uploaded file (PDF) using PDFBox
        String filename = file.getOriginalFilename();
        // Save the document to the database
        Document document = new Document();
        document.setFilename(filename);
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        try {
            return documentRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get document with id " + id, e);
        }
    }

    public Document updateDocument(Long id, MultipartFile file) throws IOException {
        // Process and store the uploaded file (PDF) using PDFBox
        String filename = file.getOriginalFilename();
        // Get the document by ID
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            document.setFilename(filename);
            return documentRepository.save(document);
        } else {
            throw new DocumentNotFoundException(id);
        }
    }


    public void deleteDocument(Long id) {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            documentRepository.deleteById(id);
        } else {
            throw new DocumentNotFoundException(id);
        }
    }

}

@ResponseStatus(HttpStatus.NOT_FOUND)
class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(Long id) {
        super("Document not found with id: " + id);
    }
}
