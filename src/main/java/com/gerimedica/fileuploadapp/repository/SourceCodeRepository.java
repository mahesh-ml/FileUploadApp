package com.gerimedica.fileuploadapp.repository;

import com.gerimedica.fileuploadapp.entity.SourceCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SourceCodeRepository extends JpaRepository<SourceCode, Long> {

    Optional<SourceCode> findByCode(String code);
}
