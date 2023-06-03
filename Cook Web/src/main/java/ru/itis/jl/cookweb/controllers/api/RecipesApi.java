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
import ru.itis.jl.cookweb.exceptions.NotFoundException;
import ru.itis.jl.cookweb.models.Ingredient;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Tags(value = {
        @Tag(name = "Recipes")
})

public interface RecipesApi {

    @Operation(summary = "Получение списка рецептов")
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
    @GetMapping(value = "/recipes", params = {"tags","page"})
    ResponseEntity<RecipePage> getRecipesByTag(@RequestParam("tags") @Parameter(description = "Рецепты с тегом") Set<ru.itis.jl.cookweb.models.Tag> tags,
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
                                    schema = @Schema(implementation = NotFoundException.class))
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
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )

    })
    @GetMapping("/recipes/my")
    ResponseEntity<RecipePage>  getRecipesByAuthor(Principal principal, @RequestParam(value = "page", defaultValue = "0") int page);

    @Operation(summary = "Добавление рецепта в избранное")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно добавлен",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipePage.class))
                    }),
            @ApiResponse(responseCode = "404", description = "У вас нет рецептов",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )

    })
    @PostMapping("/addToFavorite")
    ResponseEntity<RecipePage> addRecipeToFavourite(Principal principal, RecipeDto recipeDto, int page);

    @Operation(summary = "Поиск рецептов по ингредиентам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепты найдены!",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipePage.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Рецепты не найдены :(",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @GetMapping("/recipesByIngredients")
    ResponseEntity<RecipePage> getRecipesByIngredients(@RequestParam List<Ingredient> ingredients, int page);

    @Operation(summary = "Добавление ингредиента в рецепт")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ингредиент успешно добавлен",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Рецепт для добавления не найден",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @PostMapping("/addIngredientToRecipe")
    ResponseEntity<RecipeDto> addIngredientToRecipe(RecipeDto recipeDto, Ingredient ingredient);
}

