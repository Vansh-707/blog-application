package com.example.bro.repositories;

// import java.lang.classfile.ClassFile.Option;
import java.util.*;

import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.bro.TestDataUtil;
import com.example.bro.domain.entities.Author;
import com.example.bro.domain.entities.Books;

import static org.assertj.core.api.Assertions.assertThat; // Imp . 

@SpringBootTest
// @ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {

    private BooksRepository underTest;

    @Autowired
    public BookRepositoryIntegrationTest(BooksRepository b){ // we must create bean for class 'BooksRepository' to avoid error . 
        this.underTest = b;
    }

    @Test
    public void testThatBookCanBeCreatedAndRead(){

        Author author = TestDataUtil.createTestAuthor();
        // authorDao.create(author); // it is automaticlly handled by cascading
        Books book = TestDataUtil.createTestBook(author);
        underTest.save(book);
        Optional<Books> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

        // Assert that the version is 0 as it has just been saved.
        // assertThat(result.get().getAuthor().getVersion()).isEqualTo(0L);
        // assertThat(result.get().getAuthor().getVersion()).isNull();
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRetrieved(){

        // step 1. create author instnaces with respective id to make as primary key . 
        Author a = TestDataUtil.createTestAuthor(); // id = 1L
        Author c = TestDataUtil.createTestAuthorC(); // id = 100L

        // step 2. create test book instances with corresponding author_id as foreign key . 
        Books b1 = TestDataUtil.createTestBook(a); // author_id = 1L . 
        underTest.save(b1);
        Books b2 = TestDataUtil.createTestBookB(c); // author_id = 100L . 
        underTest.save(b2);
        Books b3 = TestDataUtil.createTestBookC(c); // author_id = 100L . 
        underTest.save(b3);

        Iterable<Books> result = underTest.findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(b1,b2,b3);
    }

    @Test
    public void testThatBooksCanBeUpdated(){
        Author a = TestDataUtil.createTestAuthor();

        Books b = TestDataUtil.createTestBook(a); // author_id :- 1L . 
        underTest.save(b);

        b.setTitle("new title");
        underTest.save(b);

        Optional<Books> result = underTest.findById(b.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(b);
    }

    @Test
    public void testThatBooksCanBeDeleted(){
        Author a = TestDataUtil.createTestAuthor();

        Books b = TestDataUtil.createTestBook(a);
        
        underTest.save(b);
        underTest.deleteById(b.getIsbn());

        Optional<Books> x = underTest.findById(b.getIsbn());
        assertThat(x).isEmpty();
    }
}
