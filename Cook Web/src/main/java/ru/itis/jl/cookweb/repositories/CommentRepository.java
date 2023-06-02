package ru.itis.jl.cookweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jl.cookweb.models.Comment;
import ru.itis.jl.cookweb.models.Recipe;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<List<Comment>> findAllByRecipe (Recipe recipe);
}
