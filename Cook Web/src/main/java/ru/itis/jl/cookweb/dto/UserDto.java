package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.Comment;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private List<Comment> comments;

    private List<Recipe> myRecipes;

    private Set<Recipe> favoriteRecipes;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .myRecipes(user.getMyRecipes())
                .comments(user.getComments())
                .favoriteRecipes(user.getFavoriteRecipes())
                .build();
    }

    public static List<UserDto> from(List<User> users) {
        return users
                .stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
