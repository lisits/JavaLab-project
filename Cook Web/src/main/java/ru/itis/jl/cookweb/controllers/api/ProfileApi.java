package ru.itis.jl.cookweb.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.jl.cookweb.dto.CommentDto;
import ru.itis.jl.cookweb.dto.RecipePage;
import ru.itis.jl.cookweb.dto.UserDto;
import ru.itis.jl.cookweb.exceptions.NotFoundException;

import java.security.Principal;
import java.util.List;

@Tags(value = {
        @Tag(name = "Profile")
})
@RequestMapping("/profile")
public interface ProfileApi {


    @Operation(summary = "Отобразить профиль пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль найден",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @GetMapping()
    ResponseEntity<UserDto> getProfilePage(Principal principal);

    @Operation(summary = "Список рецептов пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепты найдены и отображены",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Рецепты не найдены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @GetMapping("/my")
    ResponseEntity<RecipePage> getMyRecipesPage(Principal principal, int page);

    @Operation(summary = "Список комментариев пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии найдены и отображены",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(responseCode = "404", description = "Комментарии не найдены",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = NotFoundException.class))
                    }
            )
    })
    @GetMapping("/comments")
    ResponseEntity<List<CommentDto>> getCommentsPage(Principal principal);
}
