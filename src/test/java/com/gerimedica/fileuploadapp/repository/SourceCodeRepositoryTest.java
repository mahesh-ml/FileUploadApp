package com.gerimedica.fileuploadapp.repository;


import com.gerimedica.fileuploadapp.entity.SourceCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SourceCodeRepositoryTest {

    @Autowired
    SourceCodeRepository repository;
    SourceCode sourceCode1;
    SourceCode sourceCode2;
    @BeforeEach
    public void init() {

        repository.deleteAll();
        sourceCode1 = SourceCode.builder().source("test").code("1233").build();
        sourceCode2 = SourceCode.builder().source("test11").code("123223").build();
    }

    @Test
    @DisplayName("junit test for save operation")
    public void givenObject_whenSave_thenReturnSavedObject() {
        //given precondition or setup


        //when - action or behaviour that we are testing
        SourceCode savedObject = repository.save(sourceCode1);

        //then -verify the output
        assertThat(savedObject).isNotNull();
        assertThat(savedObject.getCode()).isEqualTo(sourceCode1.getCode());


    }


    @Test
    @DisplayName("junit test for find All operation")
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {
        //given precondition or setup
        repository.save(sourceCode1);
        repository.save(sourceCode2);
        //when - action or behaviour that we are testing
        List<SourceCode> srcList = repository.findAll();

        //then -verify the output
        assertThat(srcList).isNotNull();
        assertThat(srcList.size()).isEqualTo(2);

    }


    @Test
    @DisplayName("junit test for find By Code operation")
    public void givenCode_whenFindByCode_thenReturnObject() {
        //given precondition or setup

        repository.save(sourceCode1);
        //when - action or behaviour that we are testing
        SourceCode src = repository.findByCode(sourceCode1.getCode()).get();

        //then -verify the output
        assertThat(src).isNotNull();

    }

    @Test
    @DisplayName("junit test for delete  operation")
    public void givenEmployeeId_whenDelete_thenEmployeeDeleted() {
        //given precondition or setup

        repository.save(sourceCode1);

        //when - action or behaviour that we are testing
        repository.deleteAll();
        Optional<SourceCode> src = repository.findByCode(sourceCode1.getCode());

        //then -verify the output
        assertThat(src).isEmpty();

    }

}
