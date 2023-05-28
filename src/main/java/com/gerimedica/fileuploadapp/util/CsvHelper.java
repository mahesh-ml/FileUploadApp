package com.gerimedica.fileuploadapp.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
public class CsvHelper {

    public static final Function<MultipartFile , Boolean> hasCsvFormat =  (file) ->  "text/csv".equals(file.getContentType());


    public static <T> List<T> convertToModel(MultipartFile file, Class<T> responseType) {
        List<T> models;
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<?> csvToBean = new CsvToBeanBuilder<>(reader)
                    .withType(responseType)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreQuotations(true)
                    .withStrictQuotes(false)
                    .withFilter(stringValues -> Arrays.stream(stringValues)
                            .anyMatch(value -> value != null && value.length() > 0))
                    .withThrowExceptions(false)
                    .build();

            models = (List<T>) csvToBean.parse();
        } catch (Exception ex) {
            log.error("Error parsing csv file {} ", ex);
            throw new IllegalArgumentException(ex.getCause().getMessage());
        }
        return models;
    }


}
