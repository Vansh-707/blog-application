package com.example.bro.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.bro.domain.dto.AuthorDto;
import com.example.bro.domain.entities.Author;
import com.example.bro.mappers.Mapper;
import com.example.bro.services.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthorController {

    private AuthorService authorService;
    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService a, Mapper<Author, AuthorDto> authorMapper){
        this.authorService = a;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto b){ // for creating new author, we will be given json file using the request, which is converted to java object using jackson . 
        // @RequestBody :- it tells spring to look at the http request body for author objects . 

        Author x = authorMapper.mapFrom(b);
        Author savedAuthor = authorService.createAuthor(x);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }
    // ResponseEntity :- it is used bec., in some cases while testing the http response code (200) might not match with the expected code (201), so, to remove error due to wrong response code we will use ResponseEntity . 

    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors(){
        List<Author> x = authorService.findAll();

        // to convert all author entity in the list to authorDto :- 
        return x.stream()
            .map(authorMapper::mapTo)
            .collect(Collectors.toList());
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<Author> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDto.setId(id);
        Author author = authorMapper.mapFrom(authorDto);
        Author updated = authorService.createAuthor(author);
        return new ResponseEntity<>(
            authorMapper.mapTo(updated),
            HttpStatus.OK
        );

    }
}
