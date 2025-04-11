package com.example.bro.controllers;

import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.bro.TestDataUtil;
import com.example.bro.domain.dto.BookDto;
import com.example.bro.domain.entities.Books;
import com.example.bro.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
// @ExtendWith(SpringExtension.class)

public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService){
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception{
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/"+bookDto.getIsbn()) // put mapping
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception{ 
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        
        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn()) // put mapping
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus200Ok() throws Exception{

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateBookReturnsListOfBooks() throws Exception{
        Books b = TestDataUtil.createTestBookB(null);
        bookService.createBook(b.getIsbn(), b); // the dirties context will clear all test object after each test runs, 
        // so, we need to make sure that there is atleast one entry in book database to run this test . 

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(
            MockMvcResultMatchers.jsonPath("$[0].isbn").value("12345-abc")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].title").value("bro")
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200WhenBookExists() throws Exception{
        Books b = TestDataUtil.createTestBookB(null);
        bookService.createBook(b.getIsbn(), b); // to ensure atleast one entity exists in the book database . 

        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/18") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsHttpStatus200WhenBookDoesNotExistss() throws Exception{
        mockMvc.perform(
            MockMvcRequestBuilders.get("/books/180") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
        );
    }
}
