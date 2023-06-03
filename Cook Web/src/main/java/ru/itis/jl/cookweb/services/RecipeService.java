package ru.itis.jl.cookweb.services;

import org.springframework.stereotype.Service;
import ru.itis.jl.cookweb.dto.NewRecipeDto;
import ru.itis.jl.cookweb.dto.RecipeDto;
import ru.itis.jl.cookweb.dto.RecipePage;
import ru.itis.jl.cookweb.models.Ingredient;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.Tag;
import ru.itis.jl.cookweb.models.User;

import java.util.List;
import java.util.Set;

@Service
public interface RecipeService {
    RecipePage getAllRecipesSortedDesc(int page);
    RecipePage getAllRecipesSortedAsc(int page);
    RecipePage getRecipesByTag(Set<Tag> tags, int page);
    RecipeDto addRecipe(String email, NewRecipeDto newRecipeDto);
    RecipeDto getRecipe(Long id);
    RecipePage getRecipesByAuthor(String email, int page);
    RecipePage addRecipeToFavourite(String email, RecipeDto recipeDto, int page);
    RecipePage getRecipesByIngredients(List<Ingredient> ingredients, int page);
    RecipeDto addIngredientToRecipe(RecipeDto recipeDto, Ingredient ingredient);
}

