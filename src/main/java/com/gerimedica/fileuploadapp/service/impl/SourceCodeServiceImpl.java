package com.gerimedica.fileuploadapp.service.impl;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.exception.ResourceNotFoundException;
import com.gerimedica.fileuploadapp.payload.SourceInput;
import com.gerimedica.fileuploadapp.repository.SourceCodeRepository;
import com.gerimedica.fileuploadapp.service.SourceCodeService;
import com.gerimedica.fileuploadapp.util.CsvHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SourceCodeServiceImpl implements SourceCodeService {

    SourceCodeRepository sourceCodeRepository;

    @Autowired
    ModelMapper modelMapper;

    public SourceCodeServiceImpl(SourceCodeRepository sourceCodeRepository) {
        this.sourceCodeRepository = sourceCodeRepository;
    }


    public void saveContent(MultipartFile file) {
        try {
            List<SourceInput> sourceInputs = CsvHelper.convertToModel(file, SourceInput.class);
            List<SourceCode> listToSave = sourceInputs.stream().map(inp-> modelMapper.map(inp, SourceCode.class)).collect(Collectors.toList());
            sourceCodeRepository.saveAll(listToSave);

        } catch (Exception e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
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
