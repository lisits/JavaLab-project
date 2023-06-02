package ru.itis.jl.cookweb.services.impl;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.jl.cookweb.dto.NewUserDto;
import ru.itis.jl.cookweb.models.User;
import ru.itis.jl.cookweb.repositories.UserRepository;
import ru.itis.jl.cookweb.security.details.UserDetailsImpl;
import ru.itis.jl.cookweb.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    }


    @Override
    public void deleteUser(String email) {
        User user = loadUserByUsername(email);
        user.setState(User.State.DELETED);
        userRepository.save(user);
    }

    private User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User <" + email + "> not found"));
    }
}
