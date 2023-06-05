package ru.itis.jl.cookweb.security.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ru.itis.jl.cookweb.security.repositories.BlacklistRepository;

@RequiredArgsConstructor
@Repository
public class BlacklistRepositoryRedisImpl implements BlacklistRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String token) {
        redisTemplate.opsForValue().set(token, "");
    }

    @Override
    public boolean exists(String token) {
        Boolean hasToken = redisTemplate.hasKey(token);
        return hasToken != null && hasToken;
    }
}
