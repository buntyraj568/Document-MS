package com.example.Document.MS.repo;

import com.example.Document.MS.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Custom methods, if needed
}
