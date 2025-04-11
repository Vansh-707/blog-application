package com.example.bro.mappers;

// it is used to provide complete logic for mapper class . 
public interface Mapper<A, B> {

    B mapTo(A a);
    A mapFrom(B b);
}
