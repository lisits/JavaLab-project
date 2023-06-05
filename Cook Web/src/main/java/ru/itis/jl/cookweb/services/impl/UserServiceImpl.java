package ru.itis.jl.cookweb.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.jl.cookweb.dto.NewUserDto;
import ru.itis.jl.cookweb.models.EmailToken;
import ru.itis.jl.cookweb.models.User;
import ru.itis.jl.cookweb.repositories.EmailTokenRepository;
import ru.itis.jl.cookweb.repositories.UserRepository;
import ru.itis.jl.cookweb.services.UserService;
import ru.itis.jl.cookweb.utils.ElasticEmailClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String apiKey = "E04D9D4790762F59F826C1200303D123C4CB806F6A7865BF5E5964631E57D22111211392D1F301E8DB37D8BC583811D1";
    private static final String url = "http://localhost:8080/token/verify/";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailTokenRepository emailTokenRepository;

    @Override
    @Transactional
    //TODO add confirmation
    public void doRegistration(NewUserDto newUserDto) {

        if (userRepository.existsUserByEmail(newUserDto.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email address already exists");

        userRepository.save(User.builder()
                        .email(newUserDto.getEmail())
                        .firstName(newUserDto.getFirstName())
                        .lastName(newUserDto.getLastName())
                        .hashPassword(passwordEncoder.encode(newUserDto.getPassword()))
                        .role(User.Role.USER)
                        .state(User.State.NOT_CONFIRMED)
                .build());

        String newTokenString = UUID.randomUUID().toString();
        emailTokenRepository.save(EmailToken.builder()
                .user(userRepository.findByEmail(newUserDto.getEmail()).orElseThrow())
                .token(newTokenString)
                .build());
        ElasticEmailClient elasticEmailClient = new ElasticEmailClient();
        elasticEmailClient.send("ziperlin",apiKey,"danilsolovevinf@gmail.com","CookWeb",newTokenString,url+newTokenString+"/"+newUserDto.getEmail(),newUserDto.getEmail(),"true");
    }

    @Override
    public void deleteUser(String email) {
        User user = loadUserByUsername(email);
        user.setState(User.State.DELETED);
        userRepository.save(user);
    }

    @Override
    public User getProfile(String email) {
        return loadUserByUsername(email);
    }

    private User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }
}
