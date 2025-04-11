package com.example.bro.services;

import java.util.List;
import java.util.Optional;

import com.example.bro.domain.entities.Author;

public interface AuthorService {
    Author createAuthor(Author a);

    List<Author> findAll();

    Optional<Author> findOne(Long id);
    boolean isExists(Long id);
}
