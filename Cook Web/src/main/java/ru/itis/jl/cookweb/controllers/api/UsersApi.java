package ru.itis.jl.cookweb.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itis.jl.cookweb.dto.NewUserDto;
import ru.itis.jl.cookweb.models.User;

import java.security.Principal;

//TODO swagger
public interface UsersApi {

    @PostMapping("/signup")
    ResponseEntity<User> registerUser(@RequestBody NewUserDto newUserDto);

    @DeleteMapping("/account")
    ResponseEntity<?> deleteUser(Principal principal);
}
