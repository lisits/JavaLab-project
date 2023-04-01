package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data

public class RecipePage {
    private List<RecipeDto> recipes;

    private Integer totalPagesCount;
}
