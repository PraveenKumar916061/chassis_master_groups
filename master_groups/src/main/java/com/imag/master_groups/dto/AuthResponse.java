package com.imag.master_groups.dto;

import com.imag.master_groups.model.OrganizationMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private OrganizationMember organizationMember;
    private String token;
    private String refreshToken;
}