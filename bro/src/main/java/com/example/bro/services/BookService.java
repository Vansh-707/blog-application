package com.example.bro.services;

import java.util.List;
import java.util.Optional;

import com.example.bro.domain.entities.Books;

public interface BookService {
    Books createBook(String isbn, Books b);

    List<Books> findAll();

    Optional<Books> findOne(String isbn);
}
