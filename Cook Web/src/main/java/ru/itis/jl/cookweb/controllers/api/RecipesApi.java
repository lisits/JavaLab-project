package ru.itis.jl.cookweb.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.jl.cookweb.dto.ExceptionDto;
import ru.itis.jl.cookweb.dto.NewRecipeDto;
import ru.itis.jl.cookweb.dto.RecipeDto;
import ru.itis.jl.cookweb.dto.RecipePage;

import java.security.Principal;

@Tags(value = {
        @Tag(name = "Recipes")
})

public interface RecipesApi {

    @Operation(summary = "Получение списка рецептов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с существующими рецептами",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipePage.class))
                    })
    })
    @GetMapping("/recipes")
    ResponseEntity<RecipePage> getAllRecipes(
            @RequestParam("page") @Parameter(description = "Номер страницы") int page,
            @RequestParam(value = "sort", defaultValue = "asc") @Parameter(description = "Сортировать asc/desc") String sort
    );

    @Operation(summary = "Добавление рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Добавленный рецепт",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecipeDto.class))
                    }
            )
    })
    @PostMapping("/recipe")
    ResponseEntity<RecipeDto> addRecipe(Principal principal, @RequestBody NewRecipeDto newRecipeDto);



    @Operation(summary = "Получение страницы рецептов по тегу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с существующими рецептами, содержащие тэг",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipePage.class))
                    })
    })
    @GetMapping(value = "/recipes", params = {"tag","page"})
    ResponseEntity<RecipePage> getRecipesByTag(@RequestParam("tag") @Parameter(description = "Рецепты с тегом") String tagParam,
                                               @RequestParam("page")  @Parameter(description = "Номер страницы") int page);


    @Operation(summary = "Получение страницы рецпета по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )

    })
    @GetMapping(value = "/recipes/{id}")
    ResponseEntity<RecipeDto> getRecipe(@PathVariable("id") Long id);


    @Operation(summary = "Получение страницы рецептов пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мои рецепты",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "У вас нет рецептов",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionDto.class))
                    }
            )

    })
    @GetMapping("/recipes/my")
    ResponseEntity<RecipePage>  getRecipesByAuthor(Principal principal, @RequestParam(value = "page", defaultValue = "0") int page);

}

