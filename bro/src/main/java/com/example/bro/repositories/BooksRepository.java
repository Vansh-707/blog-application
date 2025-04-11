package com.example.bro.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.bro.domain.entities.Books;

@Repository
public interface BooksRepository extends CrudRepository<Books, String>{
}
