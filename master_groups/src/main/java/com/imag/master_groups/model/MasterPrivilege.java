package com.imag.master_groups.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "master_privileges")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterPrivilege {

    @Id
    @Column(name = "prvlg_id")
    private String privilegeId;
    @Column(name = "prvlgs")
    private String privileges;
    @Column(name = "is_role_specific_enable")
    private boolean isRoleSpecificEnable;
    @Column(name = "is_user_specific_enable")
    private boolean isUserSpecificEnable;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_ts")
    private Timestamp createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_ts")
    private Timestamp updatedAt;
    @Column(name = "deleted_ts")
    private Timestamp deletedAt;
    @Column(name = "status")
    private Integer status;

    @ManyToMany(mappedBy = "masterPrivileges")
    @JsonIgnore
    private List<OrganizationMember> organizationMembers=new ArrayList<OrganizationMember>();

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId.toUpperCase();
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges.toUpperCase();
    }

    public void setRoleSpecificEnable(boolean roleSpecificEnable) {
        isRoleSpecificEnable = roleSpecificEnable;
    }

    public void setUserSpecificEnable(boolean userSpecificEnable) {
        isUserSpecificEnable = userSpecificEnable;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy.toUpperCase();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy.toUpperCase();
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setOrganizationMembers(List<OrganizationMember> organizationMembers) {
        this.organizationMembers = organizationMembers;
    }
}
