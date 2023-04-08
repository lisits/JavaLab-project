package ru.itis.jl.cookweb.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;

}

