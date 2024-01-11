package com.imag.master_groups.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "master_roles")
public class MasterRole {

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_desc")
    private String roleDescription;
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
    private int status;
    @OneToMany(mappedBy = "masterRole")
    @JsonIgnore
    private List<OrganizationMember> organizationMembers = new ArrayList<OrganizationMember>();

    public void setRoleId(String roleId) {
        this.roleId = roleId.toUpperCase();
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName.toUpperCase();
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription.toUpperCase();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy.toUpperCase();
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

    public void setStatus(int status) {
        this.status = status;
    }

    public void setOrganizationMembers(List<OrganizationMember> organizationMembers) {
        this.organizationMembers = organizationMembers;
    }
}
