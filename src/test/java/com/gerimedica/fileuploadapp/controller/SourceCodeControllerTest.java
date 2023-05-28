package com.gerimedica.fileuploadapp.controller;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.service.SourceCodeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SourceCodeController.class)
public class SourceCodeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SourceCodeService sourceCodeService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void givenFiles_whenFindAll_thenReturnEntries() throws Exception {
        //given precondition or setup
        SourceCode src = SourceCode.builder().source("test").code("1233").build();
        SourceCode src1 = SourceCode.builder().source("test2").code("123223").build();

        given(sourceCodeService.getAllSourceCodeList()).willReturn(List.of(src, src1));

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc.perform(get("/api/getContent"));

        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));

    }


    @Test
    public void givenCode_whenGetByCode_thenReturnSourceObject() throws Exception {
        //given precondition or setup
        String code = "219293";
        SourceCode src = SourceCode.builder().source("test").code("1233").build();
        given(sourceCodeService.getSourceByCode(code)).willReturn(src);

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc
                .perform(get("/api/getContent/{code}", code));

        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source", is(src.getSource())))
                .andExpect(jsonPath("$.code", is(src.getCode())));


    }

    @Test
    public void givenNothing_whenDeleteAll_thenSourceDeleted() throws Exception {
        //given precondition or setup
        Mockito.doNothing().when(sourceCodeService).deleteAll();

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc.perform(delete("/api/deleteContent"));

        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk());
    }



}
