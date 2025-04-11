package com.example.bro.mappers.impl;

import com.example.bro.domain.entities.Author;
import com.example.bro.mappers.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.bro.domain.dto.AuthorDto;

@Component
public class AuthorMapperImpl implements Mapper<Author, AuthorDto> { // @Component :- we create bean for AuthorMapperImpl . 

    private ModelMapper modelMapper;

    AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(Author a){ // to convert Author object to AuthorDto object . 
        return modelMapper.map(a, AuthorDto.class);
    }

    @Override
    public Author mapFrom(AuthorDto b){  // to convert AuthorDto object to Author object . 
        return modelMapper.map(b, Author.class);
    }
}
