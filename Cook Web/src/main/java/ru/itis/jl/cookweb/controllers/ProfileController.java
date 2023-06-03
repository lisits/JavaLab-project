package ru.itis.jl.cookweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.jl.cookweb.controllers.api.ProfileApi;
import ru.itis.jl.cookweb.dto.CommentDto;
import ru.itis.jl.cookweb.dto.RecipePage;
import ru.itis.jl.cookweb.dto.UserDto;
import ru.itis.jl.cookweb.services.CommentService;
import ru.itis.jl.cookweb.services.RecipeService;
import ru.itis.jl.cookweb.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileApi {

    private final UserService userService;

    private final RecipeService recipeService;

    private final CommentService commentService;

    @Override
    public ResponseEntity<UserDto> getProfilePage(Principal principal) {
        return ResponseEntity.ok().body(UserDto.from(userService.getProfile(principal.getName())));
    }

    @Override
    public ResponseEntity<RecipePage> getMyRecipesPage(Principal principal, int page) {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipesByAuthor(principal.getName(), page));
    }

    @Override
    public ResponseEntity<List<CommentDto>> getCommentsPage(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByUser(principal));
    }

}
