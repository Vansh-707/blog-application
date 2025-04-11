package com.example.bro.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// jackson uses the noArgsConstructor and then use getter and setter to set  properties of object, so, @NoArgsConstructor is used . 

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto{

    private Long id;
    
    private String name;
    
    private Integer age;
}