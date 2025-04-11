package com.example.bro.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.example.bro.domain.entities.Author;
import com.example.bro.repositories.AuthorRepository;
import com.example.bro.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService{ // @Service :- it will create bean for AuthorServiceImpl class . 
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository a){
        this.authorRepository = a;
    }

    @Override
    public Author createAuthor(Author a){
        return authorRepository.save(a);
    }

    @Override
    public List<Author> findAll(){
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    }

    @Override
    public Optional<Author> findOne(Long id){
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id){
        return authorRepository.existsById(id);
    }
}
