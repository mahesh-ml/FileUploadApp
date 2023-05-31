package com.gerimedica.fileuploadapp.service;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.exception.ApiException;
import com.gerimedica.fileuploadapp.payload.SourceInput;
import com.gerimedica.fileuploadapp.repository.SourceCodeRepository;
import com.gerimedica.fileuploadapp.service.impl.SourceCodeServiceImpl;
import com.gerimedica.fileuploadapp.util.CsvHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SourceCodeServiceTest {


    @Mock
    SourceCodeRepository sourceCodeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    SourceCodeServiceImpl classUnderTest;

    SourceCode src;

    @BeforeEach
    public void init() {

        src = SourceCode.builder().code("tyest1233").codeListCode("tes12233").build();

    }

    @Test
    public void givenContentList_whenGetAll_thenListAllContent() {
        //given precondition or setup
        given(sourceCodeRepository.findAll()).willReturn(List.of(src));

        //when - action or behaviour that we are testing
        List<SourceCode> sourceCodeList = classUnderTest.getAllSourceCodeList();

        //then -verify the output
        assertThat(sourceCodeList).isNotNull();
        assertThat(sourceCodeList.size()).isEqualTo(1);

    }

    @Test
    public void givenContentList_whenGetAll_thenReturnEmptyList() {
        //given precondition or setup
        given(sourceCodeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or behaviour that we are testing
        List<SourceCode> sourceCodeList = classUnderTest.getAllSourceCodeList();

        //then -verify the output
        assertThat(sourceCodeList).isEmpty();
        assertThat(sourceCodeList.size()).isEqualTo(0);

    }

    @Test
    public void givenCode_whenFindByCode_thenReturnContentByCode() {
        //given precondition or setup
        given(sourceCodeRepository.findByCode("test123")).willReturn(Optional.of(src));

        //when - action or behaviour that we are testing
       SourceCode source = classUnderTest.getSourceByCode("test123");

        //then -verify the output
        assertThat(source).isNotNull();

    }

    @Test
    public void givenAll_whenDeleteContent_thenContentsDeleted() {

        //given precondition or setup
        willDoNothing().given(sourceCodeRepository).deleteAll();

        //when - action or behaviour that we are testing
        classUnderTest.deleteAll();

        //then -verify the output
        verify(sourceCodeRepository, times(1)).deleteAll();
    }


    @Test
    public void saveContent_withValidCsvFile_savesAllSourceCodes() throws Exception {
        String csvContent = "id,name\n1,Alice\n2,Bob\n";
        MockMultipartFile csvFile = new MockMultipartFile("file", "test11.csv",
                "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        when(modelMapper.map(any(), any())).thenReturn(new SourceCode());
        when(sourceCodeRepository.saveAll(any())).thenReturn(Collections.emptyList());

        classUnderTest.saveContent(csvFile);

        verify(sourceCodeRepository).saveAll(any());
    }

    @Test
    void saveContent_withInvalidCsvFile_throwsApiException() throws Exception {
        String invalidContent = "id,name,age\n1,Alice,20\n2,Bob";
        MockMultipartFile invalidFile = new MockMultipartFile("file", "invalid.csv",
                "text/csv", invalidContent.getBytes(StandardCharsets.UTF_8));



        Assertions.assertThrows(ApiException.class, () -> classUnderTest.saveContent(invalidFile));
    }


}
