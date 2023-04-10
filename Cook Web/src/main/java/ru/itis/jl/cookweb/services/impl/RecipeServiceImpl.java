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
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.User;
import ru.itis.jl.cookweb.repositories.RecipeRepository;
import ru.itis.jl.cookweb.repositories.UserRepository;
import ru.itis.jl.cookweb.services.RecipeService;

import static ru.itis.jl.cookweb.dto.RecipeDto.from;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
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
    public RecipePage getRecipesByTag(String tagParam, int page) {
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Recipe> recipePageWithTag = recipeRepository.findAllByTagContaining(tagParam, pageRequest);
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
                .tag(newRecipeDto.getTags())
                .favourite(newRecipeDto.setFavourite())
                .author(author)
                .build();
        recipeRepository.save(recipe);
        return RecipeDto.from(recipe);
    }

    private User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }

    @Override
    public RecipeDto getRecipe(Long id) {
        Recipe recipe = recipeRepository.getReferenceById(id);
        return RecipeDto.from(recipe);
    }
}
