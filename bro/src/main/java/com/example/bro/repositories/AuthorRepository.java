package com.example.bro.repositories;

import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.bro.domain.entities.Author;

// @Repository :- it creates the bean for specific repository . 

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>{

    Iterable<Author> AgeLessThan(int i); // spring jpa will automatically provides its implemenetation by readin name of func 'AgeLessThan' . 

    // HQL :- high query language . 

    // 'Author' :- java repository class . 
    @Query("SELECT a from Author a where a.age > ?1")
    Iterable<Author> AuthorsWithAgeGreaterThan(int i);
}
