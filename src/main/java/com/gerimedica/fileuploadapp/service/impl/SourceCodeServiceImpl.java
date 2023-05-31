package com.gerimedica.fileuploadapp.service.impl;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import com.gerimedica.fileuploadapp.exception.ApiException;
import com.gerimedica.fileuploadapp.exception.ResourceNotFoundException;
import com.gerimedica.fileuploadapp.payload.SourceInput;
import com.gerimedica.fileuploadapp.repository.SourceCodeRepository;
import com.gerimedica.fileuploadapp.service.SourceCodeService;
import com.gerimedica.fileuploadapp.util.ApiConstants;
import com.gerimedica.fileuploadapp.util.CsvHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SourceCodeServiceImpl implements SourceCodeService {

    final SourceCodeRepository sourceCodeRepository;


    final ModelMapper modelMapper;

    public SourceCodeServiceImpl(SourceCodeRepository sourceCodeRepository,ModelMapper modelMapper) {
        this.sourceCodeRepository = sourceCodeRepository;
        this.modelMapper=modelMapper;
    }


    public void saveContent(MultipartFile file) {
        try {
            List<SourceCode> sourceCodes = CsvHelper.convertToModel(file, SourceInput.class)
                    .stream()
                    .map(inp -> modelMapper.map(inp, SourceCode.class))
                    .collect(Collectors.toList());
            sourceCodeRepository.saveAll(sourceCodes);
        } catch (Exception ex) {
            String message = ApiConstants.ERROR_FILE_SAVE.getMessage() + ex.getMessage();
            log.error(message);
            throw new ApiException("MultipartFile", "file", file.getOriginalFilename());
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
