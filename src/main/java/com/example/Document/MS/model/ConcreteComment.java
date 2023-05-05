package com.example.Document.MS.model;

import javax.persistence.Entity;

@Entity
public class ConcreteComment extends Comment {
    private String additionalField;

    public ConcreteComment() {
        // Default constructor
    }

    public ConcreteComment(String additionalField) {
        this.additionalField = additionalField;
    }

    public String getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(String additionalField) {
        this.additionalField = additionalField;
    }
}
