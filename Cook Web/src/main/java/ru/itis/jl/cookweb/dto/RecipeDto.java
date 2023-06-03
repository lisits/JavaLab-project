package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.*;


import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class RecipeDto {
    private Long id;
    private String name;
    private Set<Tag> tags;
    private Date addedIn;
    private Long favourite;
    private String description;
    private String time;
    private Long authorId;
    private List<Long> comments;
    private Set<User> favoritedByUsers;
    private List<Ingredient> ingredients;

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
                .tags(recipe.getTags())
                .favourite(recipe.getFavourite())
                .authorId(recipe.getAuthorId())
                .favoritedByUsers(recipe.getFavoritedByUsers())
                .ingredients(recipe.getIngredients())
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
