package ru.itis.jl.cookweb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jl.cookweb.models.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    Page<Recipe> findAllByTagContaining(String tag, Pageable pageable);
}
