package com.example.CSVReader.Configuration;

import com.example.CSVReader.FieldMapper.PersonFieldSetMapper;
import com.example.CSVReader.Model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class JobConfiguration {

    /**
     * LOGGER
     */
    Logger LOGGER = LogManager.getLogger(JobConfiguration.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public FlatFileItemReader<Person> personItemReader() {

        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("/Data/Person2.csv"));

        DefaultLineMapper<Person> customLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"id", "firstName", "lastName", "birthdate", "isProcessed"});

        customLineMapper.setLineTokenizer(tokenizer);
        customLineMapper.setFieldSetMapper(new PersonFieldSetMapper());
        customLineMapper.afterPropertiesSet();
        reader.setLineMapper(customLineMapper);
        return reader;
    }

    @Bean
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public JdbcBatchItemWriter<Person> personItemWriter() {

        JdbcBatchItemWriter<Person> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("INSERT INTO PERSON VALUES (:id, :firstName, :lastName, :birthdate,:isProcessed)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();

        return itemWriter;


    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(10)
                .reader(personItemReader())
                .processor(processor())
                .writer(personItemWriter())
                .build();
    }


    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build();
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new JobProcessor();
    }


}
