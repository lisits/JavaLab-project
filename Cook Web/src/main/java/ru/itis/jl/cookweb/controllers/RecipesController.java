package ru.itis.jl.cookweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.jl.cookweb.dto.NewRecipetDto;
import ru.itis.jl.cookweb.dto.RecipeDto;
import ru.itis.jl.cookweb.dto.RecipePage;
import ru.itis.jl.cookweb.controllers.api.RecipesApi;
import ru.itis.jl.cookweb.services.RecipeService;



@RestController
@RequiredArgsConstructor
public class RecipesController implements RecipesApi {

    private final RecipeService recipeService;

//    @Override
//    public ResponseEntity<RecipePage> getAllRecipes(int page) {
//        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipes(page));
//    }

    @Override
    public ResponseEntity<RecipePage> getAllRecipes(int page,String sort) {
        if (sort.equals("desc")) {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipesSortedDesc(page));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(recipeService.getAllRecipesSortedAsc(page));
        }
    }


    @Override
    public ResponseEntity<RecipeDto> addRecipe(NewRecipetDto newRecipetDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.addRecipe(newRecipetDto));
    }

    @Override
    public ResponseEntity<RecipePage> getRecipesByTag(String tagParam, int page) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByTag(tagParam,page));
    }

    @Override
    public ResponseEntity<RecipeDto> getRecipe(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipe(id));
    }
}