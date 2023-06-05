package ru.itis.jl.cookweb.security.repositories;

public interface BlacklistRepository {
    void save(String token);

    boolean exists(String token);
}
