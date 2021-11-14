package com.example.CSVReader.Configuration;

import com.example.CSVReader.Model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class JobProcessor implements ItemProcessor<Person,Person> {

    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(JobProcessor.class);

    @Override
    public Person process(Person person) throws Exception {

        LOGGER.info("***** Inside process() in " + this.getClass().getName().toString());
        Person modifiedPerson = new Person();
        LOGGER.info("*** Object Coming as " + person);
        modifiedPerson.setId(person.getId());
        modifiedPerson.setFirstName(person.getFirstName().toUpperCase());
        modifiedPerson.setLastName(person.getLastName().toUpperCase());
        modifiedPerson.setBirthdate(person.getBirthdate());
        modifiedPerson.setIsProcessed(person.getIsProcessed());
        LOGGER.info("*** Modified Object Coming as " + modifiedPerson);

        return modifiedPerson;
    }
}
