package com.gerimedica.fileuploadapp.controller;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.response.ResponseMessage;
import com.gerimedica.fileuploadapp.service.SourceCodeService;
import com.gerimedica.fileuploadapp.util.ApiConstants;
import com.gerimedica.fileuploadapp.util.CsvHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SourceCodeController {


    final SourceCodeService sourceCodeService;

    public SourceCodeController(SourceCodeService sourceCodeService) {
        this.sourceCodeService = sourceCodeService;
    }

    @PostMapping("/uploadContent")
    public ResponseEntity<ResponseMessage> uploadContent(@RequestParam("file") MultipartFile file) {
        if (CsvHelper.hasCsvFormat.apply(file)) {
            sourceCodeService.saveContent(file);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(new ResponseMessage(ApiConstants.SUCCESS_FILE_UPLOAD.getMessage()
                            + file.getOriginalFilename()));
         } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(ApiConstants.INVALID_REQUEST_FILE_UPLOAD.getMessage()));
        }

    }

    @GetMapping("/getContent")
    public List<SourceCode> getAllContents() {
        return sourceCodeService.getAllSourceCodeList();
    }

    @GetMapping("/getContent/{code}")
    public ResponseEntity<SourceCode> findContentByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(sourceCodeService.getSourceByCode(code));
    }

    @DeleteMapping("/deleteContent")
    public ResponseEntity<String> deleteContents() {
        sourceCodeService.deleteAll();
        return ResponseEntity.ok(ApiConstants.SUCCESS_DELETE.getMessage());
    }
}
