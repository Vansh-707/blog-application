package com.example.bro.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.example.bro.domain.entities.Books;
import com.example.bro.repositories.BooksRepository;
import com.example.bro.services.BookService;

@Service
public class BookServiceImpl implements BookService{
    private BooksRepository bookRepository;

    public BookServiceImpl(BooksRepository b){
        this.bookRepository = b;
    }
    @Override
    public Books createBook(String isbn, Books book){
        book.setIsbn(isbn); // to enusre the isbn sent by user is only creeated . 
        return bookRepository.save(book);
    }

    @Override
    public List<Books> findAll(){
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    }

    @Override
    public Optional<Books> findOne(String isbn){
        return bookRepository.findById(isbn);
    }
}
