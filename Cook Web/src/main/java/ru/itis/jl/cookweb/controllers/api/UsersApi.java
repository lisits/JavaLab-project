package ru.itis.jl.cookweb.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.jl.cookweb.dto.NewUserDto;
import ru.itis.jl.cookweb.models.User;

import java.security.Principal;

@Tags(value = {
        @Tag(name = "User")
})
public interface UsersApi {

    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь заргистрирован",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
    })
    @PostMapping("/signup")
    ResponseEntity<User> registerUser(@RequestBody NewUserDto newUserDto);


    @Operation(summary = "Удалить аккаунт")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ваш аккаунт успешно удален"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован")
    })
    @DeleteMapping("/deleteAccount")
    ResponseEntity<?> deleteUser(Principal principal);
}