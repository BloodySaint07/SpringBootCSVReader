package com.example.CSVReader.FieldMapper;

import com.example.CSVReader.Model.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.sql.Date;

// FieldSetMapper<ModelClass> Interface that is used to map data obtained from a FieldSet into an object.

public class PersonFieldSetMapper implements FieldSetMapper<Person> {
    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        return new Person(fieldSet.readInt("id"),
                fieldSet.readString("firstName"),
                fieldSet.readString("lastName"),
                fieldSet.readString("birthdate"),
                fieldSet.readString("isProcessed")
        );

    }


}
