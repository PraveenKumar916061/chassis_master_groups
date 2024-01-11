package com.imag.master_groups.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterPrivilegeDto {

    private String privileges;
    private boolean isRoleSpecificEnable;
    private boolean isUserSpecificEnable;
}
