package com.gerimedica.fileuploadapp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseMessage {

    private String message;
}
