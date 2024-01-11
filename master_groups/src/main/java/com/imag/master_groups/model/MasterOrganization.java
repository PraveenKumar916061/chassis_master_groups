package com.imag.master_groups.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "master_org_type")
public class MasterOrganization {
    @Id
    @Column(name = "org_typ_id", nullable = false,unique = true)
    private String orgTypeId;

    @NotBlank(message = "the orgTypeName is Should not be Empty")
    @NotNull(message = "the orgTypeName is should not be null")
    @Column(name = "org_typ_name",unique = true )
    private String orgTypeName;


    @NotBlank(message = "the orgTypeDesc is should not be Empty")
    @NotNull(message = "the orgType Desc is should not be null")
    @Column(name = "org_typ_desc")
    private String orgDescription;


    @NotBlank(message = "the createdBy is should not be Empty")
    @NotNull(message = "the createdBy is should not be null")
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_ts")
    private LocalDateTime createdAt;

    @NotBlank(message = "the updatedBy is should not be Empty")
    @NotNull(message = "the updatedBy is should not be null")
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "updated_ts")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_ts")
    private LocalDateTime deletedAt;

    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "masterOrganization")
    @JsonIgnore
    private List<Organization> organizations = new ArrayList<Organization>();

    public void setOrgTypeId(String orgTypeId) {
        this.orgTypeId = orgTypeId.toUpperCase();
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName.toUpperCase();
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription.toUpperCase();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy.toUpperCase();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy.toUpperCase();
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

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}