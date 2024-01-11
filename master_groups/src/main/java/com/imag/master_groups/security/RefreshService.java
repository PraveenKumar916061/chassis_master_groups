package com.imag.master_groups.security;

import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.RefreshToken;
import com.imag.master_groups.model.User;
import com.imag.master_groups.repository.RefreshTokenDao;
import com.imag.master_groups.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshService {

    @Autowired
    private RefreshTokenDao refreshTokenDao;
    @Autowired
    private UserDao userDao;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userDao.findByEmail(username).get())
                .token(UUID.randomUUID().toString())
                .expiryTime(Instant.now().plusMillis(60000000))//100
                .build();
        return refreshTokenDao.save(refreshToken);
    }

    public String searchUserRefreshToken(String userId) {
        RefreshToken refreshToken = refreshTokenDao.findByUserId(userId);
        if (refreshToken == null) {
            User user = userDao.findById(userId).get();
            RefreshToken refreshToken1 = createRefreshToken(user.getEmail());
            return refreshToken1.getToken();
        }
        return refreshToken.getToken();
    }

    public RefreshToken searchRefreshToken(String token) {
        return refreshTokenDao.findByToken(token)
                .orElseThrow(() -> new UserDefinedException("Token not found"));
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryTime().isBefore(Instant.now())) {
            refreshTokenDao.delete(refreshToken);
            return createRefreshToken(refreshToken.getUser().getEmail());
        }
        return refreshToken;
    }
}
