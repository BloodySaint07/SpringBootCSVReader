package com.example.CSVReader.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private String isProcessed;

}
