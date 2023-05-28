package com.gerimedica.fileuploadapp.service.impl;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.exception.ApiException;
import com.gerimedica.fileuploadapp.exception.ResourceNotFoundException;
import com.gerimedica.fileuploadapp.payload.SourceInput;
import com.gerimedica.fileuploadapp.repository.SourceCodeRepository;
import com.gerimedica.fileuploadapp.service.SourceCodeService;
import com.gerimedica.fileuploadapp.util.CsvHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class SourceCodeServiceImpl implements SourceCodeService {

    final SourceCodeRepository sourceCodeRepository;

    @Autowired
    ModelMapper modelMapper;

    public SourceCodeServiceImpl(SourceCodeRepository sourceCodeRepository) {
        this.sourceCodeRepository = sourceCodeRepository;
    }


    public void saveContent(MultipartFile file) {
        try {
            CsvHelper.convertToModel(file, SourceInput.class)
                    .stream()
                    .map(inp -> modelMapper.map(inp, SourceCode.class))
                    .forEach(sourceCodeRepository::save);
        }catch (Exception ex) {
            log.error("Error while saving file " +ex.getMessage());
            throw new ApiException("MultipartFile" ,"file", file.getOriginalFilename());
        }

    }

    public List<SourceCode> getAllSourceCodeList() {
        return sourceCodeRepository.findAll();
    }

    @Override
    public SourceCode getSourceByCode(String code) {
        return sourceCodeRepository.findByCode(code).
                orElseThrow(()->new ResourceNotFoundException("SourceCode" ,"Code", code));
    }

    @Override
    public void deleteAll() {
       sourceCodeRepository.deleteAll();
    }

}
