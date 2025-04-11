package com.example.bro.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.bro.domain.dto.BookDto;
import com.example.bro.domain.entities.Books;
import com.example.bro.mappers.Mapper;
import com.example.bro.services.BookService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BookController {
    private Mapper<Books, BookDto> bookMapper;
    private BookService bookService;

    public BookController(Mapper<Books, BookDto> bookMapper, BookService bookService){
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }
    @PutMapping("/blogpost/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        
        Books bookEntity = bookMapper.mapFrom(bookDto);
        Books savedBook = bookService.createBook(isbn, bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBook);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/blogpost")
    public List<BookDto> listBooks(){
        List<Books> b = bookService.findAll();
        return b.stream()
            .map(bookMapper::mapTo)
            .collect(Collectors.toList());
    }

    @GetMapping(path = "/blogpost/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn){
        Optional<Books> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
