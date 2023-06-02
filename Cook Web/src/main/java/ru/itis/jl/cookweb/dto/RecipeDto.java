package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.Comment;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.User;


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
    private String description;
    private String time;
    private List<Long> comments;

    public static RecipeDto from(Recipe recipe) {
        List<Long> commentIds = recipe.getComments()
                .stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
        return RecipeDto.builder()
                .id(recipe.getId())
                .addedIn(recipe.getAddedIn())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .time(recipe.getTime())
                .tags(recipe.getTag())
                .favourite(recipe.getFavourite())
                .comments(commentIds)
                .build();
    }

    public static List<RecipeDto> from(List<Recipe> recipes) {
        return recipes
                .stream()
                .map(RecipeDto::from)
                .collect(Collectors.toList());
    }
}
