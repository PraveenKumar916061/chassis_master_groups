package com.imag.master_groups.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "organization_members")
public class OrganizationMember {

    @Id
    @Column(name = "org_mem_id")
    private String orgMemberId;
    @Column(name = "org_id")
    private String orgId;
    @Column(name = "usr_id")
    private String userId;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "prvlg_id")
    private String privilegeId;
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

    @ManyToOne
    @JoinColumn(name="org_id", referencedColumnName = "org_id", insertable = false, updatable = false)
    @JsonIgnore
    private Organization organization;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "usr_id",referencedColumnName = "usr_id",insertable = false,updatable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "role_id",referencedColumnName = "role_id",insertable = false,updatable = false)
    private MasterRole masterRole;

    @ManyToMany
    @JsonIgnore
//    @JoinColumn(name = "prvlg_id", referencedColumnName = "prvlg_id", insertable = false, updatable = false)
    private List<MasterPrivilege> masterPrivileges;

    public void setOrgMemberId(String orgMemberId) {
        this.orgMemberId = orgMemberId.toUpperCase();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId.toUpperCase();
    }

    public void setUserId(String userId) {
        this.userId = userId.toUpperCase();
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId.toUpperCase();
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId.toUpperCase();
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMasterRole(MasterRole masterRole) {
        this.masterRole = masterRole;
    }

    public void setMasterPrivileges(List<MasterPrivilege> masterPrivileges) {
        this.masterPrivileges = masterPrivileges;
    }
}
