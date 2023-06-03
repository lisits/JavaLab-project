package ru.itis.jl.cookweb.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.jl.cookweb.dto.converters.RecipeConverter;

@Configuration
public class MapStructConfig {

//    @Bean
//    public UserConverter userConverter() {
//        return Mappers.getMapper(UserConverter.class);
//    }

    @Bean
    public RecipeConverter recipeConverter() {
        return Mappers.getMapper(RecipeConverter.class);
    }

}
