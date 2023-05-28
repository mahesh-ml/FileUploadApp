package com.gerimedica.fileuploadapp.util;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiConstants {
    SUCCESS_FILE_UPLOAD("Uploaded the file successfully:"),
    INVALID_REQUEST_FILE_UPLOAD("Please upload a csv file!"),
    STRING_FORMAT_TEXT("%s not found with %s : %s"),
    ERROR_FILE_SAVE("Error while saving file "),
    ERROR_FILE_PARSE("Error parsing csv file  "),
    OPEN_API_TITLE("File Management API"),
    OPEN_API_DESC("Upload File, View content , View By Code, Delete All Endpoints"),
    OPEN_API_VERSION("1.0"),
    MEDIA_TYPE("text/csv"),
    SUCCESS_DELETE("Source Data cleared successfully");

    private String message;

}
