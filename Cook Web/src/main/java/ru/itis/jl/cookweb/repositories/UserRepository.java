package ru.itis.jl.cookweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.jl.cookweb.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.state = :state WHERE u.email = :email")
    void updateUserStateByEmail(@Param("state") User.State state, @Param("email") String email);
}
