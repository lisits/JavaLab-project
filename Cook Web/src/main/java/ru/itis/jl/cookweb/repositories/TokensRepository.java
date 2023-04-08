package ru.itis.jl.cookweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jl.cookweb.models.Token;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<Token,Long> {
    Optional<Token> findFirstByToken(String token);
}
