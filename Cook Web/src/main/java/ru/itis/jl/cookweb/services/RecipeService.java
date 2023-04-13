package ru.itis.jl.cookweb.services;

import org.springframework.stereotype.Service;
import ru.itis.jl.cookweb.dto.NewRecipeDto;
import ru.itis.jl.cookweb.dto.RecipeDto;
import ru.itis.jl.cookweb.dto.RecipePage;

@Service
public interface RecipeService {
    RecipePage getAllRecipesSortedDesc(int page);
    RecipePage getAllRecipesSortedAsc(int page);
    RecipePage getRecipesByTag(String param, int page);
    RecipeDto addRecipe(String email, NewRecipeDto newRecipeDto);
    RecipeDto getRecipe(Long id);
    RecipePage getRecipesByAuthor(String email, int page);
}

