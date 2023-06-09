package com.gerimedica.fileuploadapp.util;

import com.gerimedica.fileuploadapp.exception.ApiException;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class CsvHelper {

    public static final Function<MultipartFile , Boolean> hasCsvFormat =  (file) ->
            ApiConstants.MEDIA_TYPE.getMessage().equals(file.getContentType());



    public static <T> List<T> convertToModel(MultipartFile file, Class<T> responseType) {
        List<T> models;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            CsvToBean<?> csvToBean = new CsvToBeanBuilder<>(reader)
                    .withType(responseType)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withQuoteChar('"')
                    .withFilter(stringValues -> Arrays.stream(stringValues)
                            .anyMatch(value -> value != null && value.length() > 0))
                    .build();

            models = (List<T>) csvToBean.parse();
        } catch (Exception ex) {
            log.error(ApiConstants.ERROR_FILE_PARSE.getMessage(), ex);
            throw new ApiException("MultipartFile error", "File", ex.getMessage());
        }
        return models;
    }


}
