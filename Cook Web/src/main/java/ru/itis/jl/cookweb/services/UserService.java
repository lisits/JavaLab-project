package ru.itis.jl.cookweb.services;

import ru.itis.jl.cookweb.dto.NewUserDto;
import ru.itis.jl.cookweb.models.User;

public interface UserService {

    void doRegistration(NewUserDto newUserDto);

    User findByEmail(String email);
}
