package com.gerimedica.fileuploadapp.config;

import com.gerimedica.fileuploadapp.util.ApiConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ModelMapper modelMapper() {
        return  new ModelMapper();
    }


    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(ApiConstants.OPEN_API_TITLE.getMessage())
                        .description(ApiConstants.OPEN_API_DESC.getMessage())
                        .version(ApiConstants.OPEN_API_VERSION.getMessage()));
    }
}
