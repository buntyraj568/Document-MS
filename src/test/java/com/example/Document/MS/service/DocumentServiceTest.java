package com.example.Document.MS.service;

import com.example.Document.MS.model.Document;
import com.example.Document.MS.repo.CommentRepository;
import com.example.Document.MS.repo.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private DocumentService documentService;

    private List<Document> documentList;

    @BeforeEach
    void setUp() {
        documentList = new ArrayList<>();
        documentList.add(new Document(1L, "document1.pdf"));
        documentList.add(new Document(2L, "document2.pdf"));
        documentList.add(new Document(3L, "document3.pdf"));
    }

    @Test
    void uploadDocument_ValidFile_ReturnsDocument() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test data".getBytes());
        Document expectedDocument = new Document(4L, "test.pdf");
        when(documentRepository.save(any())).thenReturn(expectedDocument);

        Document actualDocument = documentService.uploadDocument(mockMultipartFile);

        assertNotNull(actualDocument);
        assertEquals(expectedDocument, actualDocument);
    }

    @Test
    void getAllDocuments_ReturnsDocumentList() {
        when(documentRepository.findAll()).thenReturn(documentList);

        List<Document> actualDocumentList = documentService.getAllDocuments();

        assertEquals(documentList, actualDocumentList);
    }

    @Test
    void getDocumentById_ExistingId_ReturnsDocument() {
        Long id = 1L;
        Document expectedDocument = new Document(id, "document1.pdf");
        when(documentRepository.findById(id)).thenReturn(Optional.of(expectedDocument));

        Optional<Document> actualDocument = documentService.getDocumentById(id);

        assertTrue(actualDocument.isPresent());
        assertEquals(expectedDocument, actualDocument.get());
    }

    @Test
    void getDocumentById_NonExistingId_ReturnsEmpty() {
        Long id = 100L;
        when(documentRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Document> actualDocument = documentService.getDocumentById(id);

        assertFalse(actualDocument.isPresent());
    }

    @Test
    void updateDocument_ExistingIdAndValidFile_ReturnsUpdatedDocument() throws IOException {
        Long id = 1L;
        String filename = "updated-document.pdf";
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", filename, "application/pdf", "updated test data".getBytes());
        Document existingDocument = new Document(id, "document1.pdf");
        Document expectedDocument = new Document(id, filename);
        when(documentRepository.findById(id)).thenReturn(Optional.of(existingDocument));
        when(documentRepository.save(any())).thenReturn(expectedDocument);

        Document actualDocument = documentService.updateDocument(id, mockMultipartFile);

        assertNotNull(actualDocument);
        assertEquals(expectedDocument, actualDocument);
    }
/*    @Test
    public void testDeleteDocument() {
        // Create a new document
        Document document = new Document();
        document.setFilename("test.pdf");
        documentRepository.save(document);

        // Delete the document
        documentService.deleteDocument(document.getId());

        // Check that the document has been deleted
        Optional<Document> optionalDocument = documentRepository.findById(document.getId());
        assertFalse(optionalDocument.isPresent());
    }*/

    @Test
    public void testDeleteDocumentWithInvalidId() {
        // Delete a non-existent document
        assertThrows(DocumentNotFoundException.class, () -> documentService.deleteDocument(9999L));
    }


}