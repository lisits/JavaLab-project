package ru.itis.jl.cookweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.jl.cookweb.models.Comment;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<List<Comment>> findAllByRecipe (Recipe recipe);

    @Query("select c from Comment c where c.id in :ids")
    Optional<List<Comment>> findAllById(@Param("ids") List<Long> ids);

    Optional<List<Comment>> findAllByAuthor (User user);
}
