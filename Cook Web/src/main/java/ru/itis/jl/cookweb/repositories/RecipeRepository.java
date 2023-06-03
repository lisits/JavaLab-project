package ru.itis.jl.cookweb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.jl.cookweb.models.Ingredient;
import ru.itis.jl.cookweb.models.Recipe;
import ru.itis.jl.cookweb.models.Tag;

import java.util.List;
import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    @Query("select r from Recipe r where r.tags in :tags")
    Page<Recipe> findAllByTagsContaining(@Param("tags") Set<Tag> tags, Pageable pageable);
    Page<Recipe> findAllByOrderByAddedInAsc(Pageable pageable);
    Page<Recipe> findAllByOrderByAddedInDesc(Pageable pageable);
    @Query("select r from Recipe r where r.authorId in (select u.id from User u where u.email = :email)")
    Page<Recipe> findAllByAuthorId_Email(@Param("email") String email, Pageable pageable);
    @Query("SELECT r FROM Recipe r WHERE r.ingredients IN (SELECT i FROM Ingredient i WHERE i.id IN :ingredients)")
    Page<Recipe> findAllByIngredients(@Param("ingredients") List<Long> ingredients, Pageable pageable);
}
