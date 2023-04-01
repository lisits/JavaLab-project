package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.Recipe;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data

public class RecipeDto {
    private Long id;
    private String name;
    private String tags;
    private Date addedIn;
    private Boolean favourite;

    public static RecipeDto from(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .addedIn(recipe.getAddedIn())
                .name(recipe.getName())
                .tags(recipe.getTag())
                .favourite(recipe.getFavourite())
                .build();
    }

    public static List<RecipeDto> from(List<Recipe> lessons) {
        return lessons
                .stream()
                .map(RecipeDto::from)
                .collect(Collectors.toList());
    }
}
