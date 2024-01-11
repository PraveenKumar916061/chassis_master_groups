package com.imag.master_groups.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "token")
    private String token;
    @Column(name = "expiry_time")
    private Instant expiryTime;

    @OneToOne
    @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")
    private User user;
}
