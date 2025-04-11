package com.example.bro.domain.entities;

import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "books")
public class Books {

    @Id
    private String isbn;

    private String title;

    // cascade :- it is used to maintain data consistency, if we retireve author info from book object, and, change it, then, the new author data will be reflected in all its instances and author table . 
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author; // we can use direct author object instead of author id, bec., we are using jpa . 
}

