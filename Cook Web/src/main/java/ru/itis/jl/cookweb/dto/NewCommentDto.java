package ru.itis.jl.cookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.jl.cookweb.models.Comment;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCommentDto {
    private Long id;
    private String text;
    private int rating;
    private LocalDateTime addedIn;
    private Recipe recipe;
    private List<String> images = new ArrayList<>();
    public LocalDateTime setAddedIn() {
        return LocalDateTime.now();
    }

}
