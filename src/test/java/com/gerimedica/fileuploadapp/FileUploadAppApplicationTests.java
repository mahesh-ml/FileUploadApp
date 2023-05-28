package com.gerimedica.fileuploadapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.repository.SourceCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FileUploadAppApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SourceCodeRepository sourceCodeRepository;

    @Autowired
    ObjectMapper objectMapper;
    SourceCode src;
    @BeforeEach
    void setup() {
        sourceCodeRepository.deleteAll();
        src = SourceCode.builder().source("test").code("1233").build();

    }

    @Test
    public void givenFiles_whenSave_thenSaveTheCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test contract.csv","text/csv",
                "test,1212,test22".getBytes());


        mockMvc.perform(
                        multipart("/api/uploadContent")
                                .file(file))
                .andExpect(status().isCreated());


    }

    @Test
    public void givenCode_whenGetByCode_thenReturnSourceObject() throws Exception {
        //given
        SourceCode saved = sourceCodeRepository.save(src);
        //when - action or behaviour that we are testing

        ResultActions result = mockMvc
                .perform(get("/api/getContent/{code}", saved.getCode()));

        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source", is(src.getSource())))
                .andExpect(jsonPath("$.code", is(src.getCode())));


    }

    @Test
    public void givenFiles_whenFindAll_thenReturnEntries() throws Exception {
        //given precondition or setup
        SourceCode src = SourceCode.builder().source("test").code("1233").build();
        SourceCode src1 = SourceCode.builder().source("test2").code("123223").build();
        sourceCodeRepository.saveAll(List.of(src,src1));

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc.perform(get("/api/getContent"));

        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));

    }

    @Test
    public void givenNothing_whenDeleteAll_thenSourceDeleted() throws Exception {
        //given precondition or setup
        SourceCode src = SourceCode.builder().source("test").code("1233").build();
        SourceCode src1 = SourceCode.builder().source("test2").code("123223").build();
        sourceCodeRepository.saveAll(List.of(src,src1));

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc.perform(delete("/api/deleteContent"));
        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk());


    }


}
