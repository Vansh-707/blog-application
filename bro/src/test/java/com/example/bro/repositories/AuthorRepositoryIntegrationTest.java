package com.example.bro.repositories;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.bro.TestDataUtil;
import com.example.bro.domain.entities.Author;
import com.example.bro.repositories.AuthorRepository;

import static org.assertj.core.api.Assertions.assertThat; // Imp

@SuppressWarnings("unused")
@SpringBootTest
// @ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {
    
    private AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorRepository y){  
        this.underTest = y;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRetrieved(){
        Author a = TestDataUtil.createTestAuthor();
        underTest.save(a); 
        Optional<Author> result = underTest.findById(a.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(a); 
    }

    // save() :- the default method provided by spring jpa to insert new record . 
    // findById() :- the default method to find recored by 'sequence id' . 

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndRetrieved(){
        Author a = TestDataUtil.createTestAuthor();
        underTest.save(a);
        Author b = TestDataUtil.createTestAuthorB();
        underTest.save(b);
        Author c = TestDataUtil.createTestAuthorC();
        underTest.save(c);

        Iterable<Author> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(a,b,c);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        Author a = TestDataUtil.createTestAuthor();
        underTest.save(a); // dsave is used for both create, and, update operation in spring jpa . 
        a.setName("new name");

        underTest.save(a);

        Optional<Author> result = underTest.findById(a.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(a);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author a = TestDataUtil.createTestAuthor();
        underTest.save(a);
        underTest.deleteById(a.getId());

        Optional<Author> result = underTest.findById(a.getId());
        assertThat(result).isEmpty();
    }

    // custom queries :- 

    @Test
    public void testThatAuthorsWithAgeLessThan(){
        Author a = TestDataUtil.createTestAuthor();
        underTest.save(a);
        Author b = TestDataUtil.createTestAuthorB();
        underTest.save(b);
        Author c = TestDataUtil.createTestAuthorC();
        underTest.save(c);

        Iterable<Author> result = underTest.AgeLessThan(55);
        assertThat(result).containsExactly(a,b);
    }

    @Test
    public void testThatAuthorsWithAgeGreaterThan(){
        Author a = TestDataUtil.createTestAuthor();
        underTest.save(a);
        Author b = TestDataUtil.createTestAuthorB();
        underTest.save(b);
        Author c = TestDataUtil.createTestAuthorC();
        underTest.save(c);

        Iterable<Author> result = underTest.AuthorsWithAgeGreaterThan(75); // the func "AuthorsWithAgeGreaterThan" will not have its implementation provided automatically, 
        // as, its name will not be decoded properly, and, query cannot be genrated by spring jpa, so, we have to provide the manual implementatio of it . 
        
        // this process is called 'HQL' using @Query annotation . 

        assertThat(result).containsExactly(c);
    }
}


