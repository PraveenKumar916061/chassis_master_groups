package com.imag.master_groups.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationMembersDto {

    private String orgId;
    private String userId;
    private String roleId;
    private String privilegeId;

}