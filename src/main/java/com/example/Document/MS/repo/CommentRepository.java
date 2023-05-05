package com.example.Document.MS.repo;

import com.example.Document.MS.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Add any additional methods specific to the Comment entity
}
