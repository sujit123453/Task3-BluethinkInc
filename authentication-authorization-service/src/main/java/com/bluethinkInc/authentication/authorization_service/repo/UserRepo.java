package com.bluethinkInc.authentication.authorization_service.repo;

import com.bluethinkInc.authentication.authorization_service.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(@Email(message = "Invalid email") @NotBlank(message = "Email is required") String email);
    java.util.Optional<User> findByEmail(String email);

    boolean existsByPhone(@Pattern(regexp = "^\\d{10}$", message = "Phone must be 10 digits") @NotBlank(message = "Phone is required") String phone);

    Optional<User> findByPhone(String phone);
}
