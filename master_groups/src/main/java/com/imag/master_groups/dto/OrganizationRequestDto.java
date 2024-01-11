package com.imag.master_groups.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequestDto {

    private String orgName;
    private String orgDesc;
    private String orgTypeId;

}
