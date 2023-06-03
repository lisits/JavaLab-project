package ru.itis.jl.cookweb.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.itis.jl.cookweb.dto.CommentDto;
import ru.itis.jl.cookweb.dto.NewCommentDto;
import ru.itis.jl.cookweb.models.Recipe;

import java.security.Principal;
import java.util.List;

@Service
public interface CommentService {
    List<CommentDto> getAllCommentsByRecipe(Recipe recipe);
    CommentDto addComment(String email, NewCommentDto newCommentDto);

    void deleteComment(String email, Long commentId);

    List<CommentDto> getAllCommentsByUser(Principal principal);
}
