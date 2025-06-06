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
import com.example.bro.domain.entities.Author;
import com.example.bro.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

// Mock MVC :- it is the method for testing controllers . 

// @AutoCinfigureMockMvc :- it will create the instance of Mock MVC, and, makes it avilable in the application context for use . 

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// @ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc

public class AuthorControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper; // the mock mvc .content() method takes string or json as input, but, we will have test Author object as input, so, to convert it to string we use the object mapper . 

    private AuthorService authorService;
    @Autowired
    AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService){
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccesfullyReturnsHttp201Created() throws Exception{ // mock mvc throws exception . 
        Author a = TestDataUtil.createTestAuthorB();
        // a.setId(null); // bec., the author id is autogenerated . 

        String authorJson = objectMapper.writeValueAsString(a);
        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors") // post mapping
            .contentType(MediaType.APPLICATION_JSON)
            .content(authorJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );

        // andExcept :- it is similar to assertThat() method . 
    }

    @Test
    public void testThatCreateAuthorSuccesfullyReturnsSavedAuthor() throws Exception{ // mock mvc throws exception . 
        Author a = TestDataUtil.createTestAuthorB();
        // a.setId(null); // bec., the author id is autogenerated . 

        String authorJson = objectMapper.writeValueAsString(a);
        mockMvc.perform(
            MockMvcRequestBuilders.post("/authors") // post mapping
            .contentType(MediaType.APPLICATION_JSON)
            .content(authorJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("tony")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(50)
        );

        // jsonPath :- it uses the expression to look within the json file, $.id :- look for id in the root json object . 
    }

    @Test
    public void testThatListAuthorsRetursnHttpStatus200() throws Exception{
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsRetursListOfAuthors() throws Exception{
        Author a = TestDataUtil.createTestAuthorB(); // the dirtesContext anotation will clean all previous test data, and, not actual data is stored in database, sow, if we run this test without any objectc in database it will give "no value at json path "$[0].id" . " . 
        // so, we need to ensure that some data is always stored in database . 
        authorService.createAuthor(a); // so, we have created new author manually in the database for test purpose . 

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].name").value("abc") // it is the name, age, and, id of the first entry retreived from database suign findAll() method . 
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].age").value(100)
        );
    }

    @Test
    public void testThatGetAuthorsRetursnHttpStatus200WhenAuthorExist() throws Exception{
        Author a = TestDataUtil.createTestAuthorB(); // the dirtesContext anotation will clean all previous test data, and, not actual data is stored in database, sow, if we run this test without any objectc in database it will give "no value at json path "$[0].id" . " . 
        // so, we need to ensure that some data is always stored in database . 
        authorService.createAuthor(a); // so, we have created new author manually in the database for test purpose . 

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/50") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorsRetursnHttpStatus2404WhenMoAuthorIsFound() throws Exception{
        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/99") // get mapping :- suthor id 99 does not exist, so, we check it is not found should be true . 
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorsRetursAuthorWhenAuthorExist() throws Exception{
        Author a = TestDataUtil.createTestAuthorB(); 
        authorService.createAuthor(a);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/authors/50") // get mapping
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(50)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("tony") // it is the name, age, and, id of the first entry retreived from database suign findAll() method . 
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.age").value(50)
        );
    }
}
