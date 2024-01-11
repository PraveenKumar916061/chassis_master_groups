package com.imag.master_groups.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @Column(name = "org_id")
    private String orgId;
    @Column(name = "org_name")
    private String orgName;
    @Column(name = "org_desc")
    private String orgDesc;
    @Column(name = "org_typ_id")
    private String orgTypId;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_ts")
    private LocalDateTime createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_ts")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_ts")
    private LocalDateTime deletedAt;
    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn (name="org_typ_id", referencedColumnName = "org_typ_id", insertable = false, updatable = false)
    @JsonIgnore
    private MasterOrganization  masterOrganization;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<OrganizationMember> organizationMembers=new ArrayList<OrganizationMember>();

    public void setOrgId(String orgId) {
        this.orgId = orgId.toUpperCase();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName.toUpperCase();
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc.toUpperCase();
    }

    public void setOrgTypId(String orgTypId) {
        this.orgTypId = orgTypId.toUpperCase();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy.toUpperCase();
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy.toUpperCase();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMasterOrganization(MasterOrganization masterOrganization) {
        this.masterOrganization = masterOrganization;
    }

    public void setOrganizationMembers(List<OrganizationMember> organizationMembers) {
        this.organizationMembers = organizationMembers;
    }
}
