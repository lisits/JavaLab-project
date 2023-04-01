package ru.itis.jl.cookweb.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.jl.cookweb.dto.NewRecipetDto;
import ru.itis.jl.cookweb.dto.RecipeDto;
import ru.itis.jl.cookweb.dto.RecipePage;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.repositories.RecipeRepository;
import ru.itis.jl.cookweb.services.RecipeService;

import static ru.itis.jl.cookweb.dto.RecipeDto.from;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Value("${default.page-size}")
    private int defaultPageSize;

    @Override
    public RecipePage getAllRecipes(int page){
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipesPage = recipeRepository.findAll(pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipesPage.getContent()))
                .totalPagesCount(recipesPage.getTotalPages())
                .build();
    }

    @Override
    public RecipePage getRecipesByTag(String tagParam, int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipePageWithTag = recipeRepository.findAllByTagContaining(tagParam, pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipePageWithTag.getContent()))
                .totalPagesCount(recipePageWithTag.getTotalPages())
                .build();
    }

    @Override
    public RecipeDto addRecipe(NewRecipetDto newRecipetDto) {
        Recipe recipe = Recipe.builder()
                .name(newRecipetDto.getName())
                .addedIn(newRecipetDto.setAddedIn())
                .tag(newRecipetDto.getTags())
                .favourite(newRecipetDto.setFavourite())
                .build();
        recipeRepository.save(recipe);
        return RecipeDto.from(recipe);
    }

    @Override
    public RecipeDto getRecipe(Long id) {
        Recipe recipe = recipeRepository.getReferenceById(id);
        return RecipeDto.from(recipe);
    }
}
