package ru.itis.jl.cookweb.services.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.jl.cookweb.dto.NewRecipeDto;
import ru.itis.jl.cookweb.dto.RecipeDto;
import ru.itis.jl.cookweb.dto.RecipePage;
import ru.itis.jl.cookweb.dto.converters.RecipeConverter;
import ru.itis.jl.cookweb.models.Ingredient;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.Tag;
import ru.itis.jl.cookweb.models.User;
import ru.itis.jl.cookweb.repositories.CommentRepository;
import ru.itis.jl.cookweb.repositories.RecipeRepository;
import ru.itis.jl.cookweb.repositories.UserRepository;
import ru.itis.jl.cookweb.services.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.itis.jl.cookweb.dto.RecipeDto.from;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private static final Logger logger = LogManager.getLogger(RecipeServiceImpl.class);

    @Value("${default.page-size}")
    private int defaultPageSize;

    @Override
    public RecipePage getAllRecipesSortedDesc(int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipesPage = recipeRepository.findAllByOrderByAddedInDesc(pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipesPage.getContent()))
                .totalPagesCount(recipesPage.getTotalPages())
                .build();
    }

    @Override
    public RecipePage getAllRecipesSortedAsc(int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipesPage = recipeRepository.findAllByOrderByAddedInAsc(pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipesPage.getContent()))
                .totalPagesCount(recipesPage.getTotalPages())
                .build();
    }

    @Override
    public RecipePage getRecipesByTag(Set<Tag> tags, int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipePageWithTag = recipeRepository.findAllByTagsContaining(tags, pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipePageWithTag.getContent()))
                .totalPagesCount(recipePageWithTag.getTotalPages())
                .build();
    }

    @Override
    public RecipeDto addRecipe(String email, NewRecipeDto newRecipeDto) {
        User author  = loadUserByUsername(email);
        Recipe recipe = Recipe.builder()
                .name(newRecipeDto.getName())
                .addedIn(newRecipeDto.setAddedIn())
                .tags(newRecipeDto.getTags())
                .favourite(0L)
                .authorId(author.getId())
                .build();
        recipeRepository.save(recipe);
        return RecipeDto.from(recipe);
    }

    private User loadUserByUsername(String email) throws UsernameNotFoundException {
        return findUser(email);
    }

    @Override
    public RecipeDto getRecipe(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return RecipeDto.from(recipe.orElseThrow());
    }

    @Override
    public RecipePage getRecipesByAuthor(String email, int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipePage = recipeRepository.findAllByAuthorId_Email(email, pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipePage.getContent()))
                .totalPagesCount(recipePage.getTotalPages())
                .build();
    }
    @Override
    public RecipePage addRecipeToFavourite(String email, RecipeDto recipeDto, int page) {

        User user = findUser(email);
        Set<Recipe> favRecipes = user.getFavoriteRecipes();
        Recipe recipe = Recipe.builder()
                .id(recipeDto.getId())
                .name(recipeDto.getName())
                .tags(recipeDto.getTags())
                .description(recipeDto.getDescription())
                .favourite(recipeDto.getFavourite())
                .time(recipeDto.getTime())
                .favoritedByUsers(recipeDto.getFavoritedByUsers())
                .comments(commentRepository.findAllById(recipeDto.getComments()).orElseThrow())
                .addedIn(recipeDto.getAddedIn())
                .ingredients(recipeDto.getIngredients())
                .authorId(recipeDto.getAuthorId())
                .build();
        favRecipes.add(recipe);
        userRepository.save(user);
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipePage = recipeRepository.findAllByAuthorId_Email(email, pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipePage.getContent()))
                .totalPagesCount(recipePage.getTotalPages())
                .build();
    }

    @Override
    public RecipePage getRecipesByIngredients(List<Ingredient> ingredients, int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        List<Long> ids = new ArrayList<>();
        ingredients.forEach(ingredient -> ids.add(ingredient.getId()));
        int ingredientsCount = ids.size();
        Page<Recipe> recipePage = recipeRepository.findAllByIngredients(ids, pageRequest);
        return RecipePage.builder()
                .recipes(RecipeDto.from(recipePage.getContent()))
                .totalPagesCount(recipePage.getTotalPages())
                .build();
    }

    @Override
    public RecipeDto addIngredientToRecipe(RecipeDto recipeDto, Ingredient ingredient) {
        Recipe recipe = recipeRepository.getReferenceById(recipeDto.getId());
        List<Ingredient> ingredients = recipe.getIngredients();
        ingredients.add(ingredient);
        recipe.setIngredients(ingredients);
        return from(recipe);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }
}
