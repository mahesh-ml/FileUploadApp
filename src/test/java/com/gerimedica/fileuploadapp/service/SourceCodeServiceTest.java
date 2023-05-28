package com.gerimedica.fileuploadapp.service;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.exception.ApiException;
import com.gerimedica.fileuploadapp.payload.SourceInput;
import com.gerimedica.fileuploadapp.repository.SourceCodeRepository;
import com.gerimedica.fileuploadapp.service.impl.SourceCodeServiceImpl;
import com.gerimedica.fileuploadapp.util.CsvHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SourceCodeServiceTest {


    @Mock
    SourceCodeRepository sourceCodeRepository;

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
    public void givenAll_whenSaveContentCsvError_thenContentsNotSaved() {

        //given precondition or setup
        MultipartFile file = new MockMultipartFile(
                "file",
                "test contract.pdf",MediaType.APPLICATION_JSON_VALUE,
                "test,1212,test22".getBytes());
        try (MockedStatic<CsvHelper> mockedStatic = Mockito.mockStatic(CsvHelper.class)) {
            mockedStatic.when(() -> CsvHelper.convertToModel(file, SourceInput.class))
                    .thenThrow(new ApiException("test","test","test"));
        }

        //when - action or behaviour that we are testing
        classUnderTest.saveContent(file);

        //then -verify the output
        verify(sourceCodeRepository, times(0)).save(src);
    }


}
