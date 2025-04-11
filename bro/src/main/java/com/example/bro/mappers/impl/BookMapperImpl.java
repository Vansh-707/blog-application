package com.example.bro.mappers.impl;


import com.example.bro.domain.entities.Books;
import com.example.bro.mappers.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.bro.domain.dto.BookDto;

@Component
public class BookMapperImpl implements Mapper<Books, BookDto>{
    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public BookDto mapTo(Books book){
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public Books mapFrom(BookDto bookDto){
        return modelMapper.map(bookDto, Books.class);
    }
}
