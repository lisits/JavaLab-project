package ru.itis.jl.cookweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.jl.cookweb.controllers.api.UsersApi;
import ru.itis.jl.cookweb.dto.NewUserDto;
import ru.itis.jl.cookweb.models.EmailToken;
import ru.itis.jl.cookweb.models.User;
import ru.itis.jl.cookweb.repositories.EmailTokenRepository;
import ru.itis.jl.cookweb.repositories.UserRepository;
import ru.itis.jl.cookweb.services.UserService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UserService userService;

    private final EmailTokenRepository emailTokenRepository;

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<User> registerUser(NewUserDto newUserDto) {
        userService.doRegistration(newUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> verifyToken(String acceptedToken, String email) {
        Optional<EmailToken> token = emailTokenRepository.findByToken(acceptedToken);
        if (token.isPresent()) {
            userRepository.updateUserStateByEmail(User.State.CONFIRMED, email);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
