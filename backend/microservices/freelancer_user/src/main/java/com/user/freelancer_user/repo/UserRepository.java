package com.user.freelancer_user.repo;

import com.user.freelancer_user.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByUserName(@Email String userName);

    Optional<Object> findByUserName(String email);
}
