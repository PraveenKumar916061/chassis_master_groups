package com.imag.master_groups.dto;

import com.imag.master_groups.model.GroupDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterGroupResponse {

    private String groupId;
    private GroupDetails groupDetails;
    private String createdBy;
    private Timestamp createdAt;
    private String updatedBy;
    private Timestamp updatedAt;

}
