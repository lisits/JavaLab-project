package ru.itis.jl.cookweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jl.cookweb.models.EmailToken;

import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByToken (String token);
}