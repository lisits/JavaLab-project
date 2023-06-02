package ru.itis.jl.cookweb.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.jl.cookweb.dto.CommentDto;
import ru.itis.jl.cookweb.dto.NewCommentDto;
import ru.itis.jl.cookweb.models.Comment;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.User;
import ru.itis.jl.cookweb.repositories.CommentRepository;
import ru.itis.jl.cookweb.repositories.RecipeRepository;
import ru.itis.jl.cookweb.repositories.UserRepository;
import ru.itis.jl.cookweb.services.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentDto> getAllCommentsByRecipe(Recipe recipe) {
        if (commentRepository.findAllByRecipe(recipe).isEmpty())
            return null;
        else {
            return CommentDto.from(commentRepository.findAllByRecipe(recipe).get());
        }
    }

    @Override
    public CommentDto addComment(String email, NewCommentDto newCommentDto) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return CommentDto.from(newCommentDto, user);
    }

    @Override
    public void deleteComment(String email, Long commentId) {
        if (commentRepository.findById(commentId).isPresent()) {
            commentRepository.deleteById(commentId);
        }
    }

}
