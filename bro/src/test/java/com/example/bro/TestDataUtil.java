package com.example.bro;

import com.example.bro.domain.dto.AuthorDto;
import com.example.bro.domain.dto.BookDto;
import com.example.bro.domain.entities.Author;
import com.example.bro.domain.entities.Books;

public class TestDataUtil {
    private TestDataUtil(){
    }

    public static Author createTestAuthor(){
        return Author.builder()
                .id(1L)
                .name("bro")
                .age(10)
                .build();
    }

    public static Author createTestAuthorB(){
        return Author.builder()
                .id(50L)
                .name("tony")
                .age(50)
                .build();
    }

    public static Author createTestAuthorC(){
        return Author.builder()
                .id(100L)
                .name("pizza")
                .age(150)
                .build();
    }

    public static AuthorDto createTestAuthorDto(){ // AuthorDto test object . 
        return AuthorDto.builder()
                .id(50L)
                .name("tony")
                .age(50)
                .build();
    }

    public static Books createTestBook(Author a){
        return Books.builder()
            .isbn("123")
            .title("bro")
            .author(a)
            .build();
            // we will use Author object, since, jpa entity uses Author object to get author id for Books class . 
    }

    public static Books createTestBookB(Author a){
        return Books.builder()
            .isbn("45")
            .title("brother")
            .author(a)
            .build();
    }

    public static Books createTestBookC(Author a){
        return Books.builder()
            .isbn("18")
            .title("pasta")
            .author(a)
            .build();
    }

    public static BookDto createTestBookDto(AuthorDto a){ // BookDto test object . 
        return BookDto.builder()
            .isbn("18")
            .title("pasta")
            .author(a)
            .build();
    }
}
