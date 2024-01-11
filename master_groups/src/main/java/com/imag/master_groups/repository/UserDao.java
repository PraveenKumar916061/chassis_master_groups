package com.imag.master_groups.repository;

import com.imag.master_groups.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    List<User> findByStatus(int status);

    Optional<User> findByEmail(String username);

    User findByEmailAndPassword(String username, String password);
}
