package com.bluethinkInc.authentication.authorization_service.repo;

import com.bluethinkInc.authentication.authorization_service.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(@Email(message = "Invalid email") @NotBlank(message = "Email is required") String email);
    java.util.Optional<User> findByEmail(String email);
}
