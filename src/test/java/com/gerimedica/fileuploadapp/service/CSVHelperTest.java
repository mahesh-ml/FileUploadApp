package com.gerimedica.fileuploadapp.service;

import com.gerimedica.fileuploadapp.exception.ApiException;
import com.gerimedica.fileuploadapp.payload.SourceInput;
import com.gerimedica.fileuploadapp.util.CsvHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class CSVHelperTest {

    @Test
    void convertToModel_withValidCsvFile_returnsListOfObjects() throws Exception {
        String csvContent = "\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n" +
                "\"ZIB\",\"ZIB001\",\"271636001\",\"Polsslag regelmatig\",\"The long description is necessary\",\"01-01-2019\",\"\",\"1\"\n";
        MockMultipartFile csvFile = new MockMultipartFile("file", "test.csv",
                "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        List<SourceInput> models = CsvHelper.convertToModel(csvFile, SourceInput.class);

        assertEquals(1, models.size());
        assertEquals("SourceInput(source=ZIB, codeListCode=ZIB001, code=271636001, displayValue=Polsslag regelmatig, longDescription=The long description is necessary, " +
                "fromDate=2019-01-01, toDate=null, sortingPriority=1)", models.get(0).toString());
    }

    @Test
    void convertToModel_withInvalidCsvFile_throwsApiException() throws Exception {
        String csvContent = "\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n" +
                "\"ZIB\",\"ZIB001\",\"Polsslag regelmatig\",\"The long description is necessary\",\"01-01-2019\",\"\",\"1\"";
        MockMultipartFile csvFile = new MockMultipartFile("file", "test.csv",
                "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));
        Exception ex = assertThrows(ApiException.class,
                () -> CsvHelper.convertToModel(csvFile, SourceInput.class));

        String expectedMessage = "MultipartFile error not found with File : Error parsing CSV line: 2. [ZIB,ZIB001,Polsslag regelmatig,The long description is necessary,01-01-2019,,1]";
        assertEquals(expectedMessage, ex.getMessage());
    }
}
