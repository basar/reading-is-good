package com.basarc.readingisgood.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadingIsGoodConfiguration {

    @Bean
    public ModelMapper provideModelMapper(){
        return new ModelMapper();
    }



}
