package ru.itis.jl.cookweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.jl.cookweb.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsUserByEmail(String email);
}
