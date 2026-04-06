package com.zorvyn.Modules.User.Repository;

import com.zorvyn.Modules.User.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRepo extends JpaRepository<User,Long>{

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
