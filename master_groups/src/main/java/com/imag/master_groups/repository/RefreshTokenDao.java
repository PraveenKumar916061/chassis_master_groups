package com.imag.master_groups.repository;

import com.imag.master_groups.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenDao extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    @Query(value = "select * from refresh_token where usr_id=:userId ", nativeQuery = true)
    RefreshToken findByUserId(@Param("userId") String userId);
}
