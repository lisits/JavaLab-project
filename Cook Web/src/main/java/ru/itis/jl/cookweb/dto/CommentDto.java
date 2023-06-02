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
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class CommentDto {
    private Long id;
    private String text;
    private int rating;
    private User author;
    private Recipe recipe;
    private LocalDateTime addedIn;
    private List<String> images = new ArrayList<>();

    public static CommentDto from(Comment comment) {
        return CommentDto.builder()
                .author(comment.getAuthor())
                .recipe(comment.getRecipe())
                .addedIn(comment.getAddedIn())
                .id(comment.getId())
                .rating(comment.getRating())
                .text(comment.getText())
                .images(comment.getImages()).build();
    }

    public static CommentDto from(NewCommentDto comment, User author) {
        return CommentDto.builder()
                .author(author)
                .recipe(comment.getRecipe())
                .addedIn(comment.getAddedIn())
                .id(comment.getId())
                .rating(comment.getRating())
                .text(comment.getText())
                .images(comment.getImages()).build();
    }
    public static List<CommentDto> from(List<Comment> comments) {
        return comments
                .stream()
                .map(CommentDto::from)
                .collect(Collectors.toList());
    }

}
