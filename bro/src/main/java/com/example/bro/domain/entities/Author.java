package com.example.bro.domain.entities;

import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Id :- the unique identifier for each row . 
// @GenratedValue :- to automaticlly generate the new id number for every new author entity inserted in the database . 

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "authors")
public class Author {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "author_id_seq")
    private Long id;

    private String name;

    private Integer age;

    // @Version
    // private Long version;
}