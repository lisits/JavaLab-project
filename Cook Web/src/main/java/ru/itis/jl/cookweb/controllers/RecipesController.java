package ru.itis.jl.cookweb.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.jl.cookweb.dto.NewRecipeDto;
import ru.itis.jl.cookweb.dto.RecipeDto;
import ru.itis.jl.cookweb.dto.RecipePage;
import ru.itis.jl.cookweb.controllers.api.RecipesApi;
import ru.itis.jl.cookweb.services.RecipeService;
import ru.itis.jl.cookweb.services.impl.RecipeServiceImpl;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
public class RecipesController implements RecipesApi {
    private static final Logger logger = LogManager.getLogger(RecipeServiceImpl.class);
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
    public ResponseEntity<RecipeDto> addRecipe(Principal principal, NewRecipeDto newRecipeDto) {
        logger.warn(principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.addRecipe(principal.getName(), newRecipeDto));
    }

    @Override
    public ResponseEntity<RecipePage> getRecipesByTag(String tagParam, int page) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByTag(tagParam,page));
    }

    @Override
    public ResponseEntity<RecipeDto> getRecipe(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipe(id));
    }

    @Override
    public ResponseEntity<RecipePage> getRecipesByAuthor(Principal principal, int page) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByAuthor(principal.getName(), page));
    }
}