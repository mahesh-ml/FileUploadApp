package com.gerimedica.fileuploadapp.service;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SourceCodeService {

    void saveContent(MultipartFile file);

    List<SourceCode> getAllSourceCodeList();

    SourceCode getSourceByCode(String code);

    void deleteAll();
}
