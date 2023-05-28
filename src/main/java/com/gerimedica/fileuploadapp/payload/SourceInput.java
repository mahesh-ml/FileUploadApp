package com.gerimedica.fileuploadapp.payload;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class SourceInput {
    @CsvBindByName(column = "source")
    private String source;

    @CsvBindByName(column = "codeListCode")
    private String codeListCode;

    @CsvBindByName(column = "code")
    private String code;

    @CsvBindByName(column = "displayValue")
    private String displayValue;

    @CsvBindByName(column = "longDescription")
    private String longDescription;

    @CsvDate(value = "dd-MM-yyyy")
    @CsvBindByName(column = "fromDate")
    private LocalDate fromDate;

    @CsvDate(value = "dd-MM-yyyy")
    @CsvBindByName(column = "toDate")
    private LocalDate toDate;

    @CsvBindByName(column = "sortingPriority")
    private String sortingPriority;
}
