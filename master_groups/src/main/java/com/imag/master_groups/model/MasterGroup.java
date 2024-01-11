package com.imag.master_groups.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "master_group")
public class MasterGroup implements Serializable {

    @Id
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "group_details")
    private String groupDetails;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public void setGroupId(String groupId) {
        this.groupId = groupId.toUpperCase();
    }

    public void setGroupDetails(String groupDetails) {
        this.groupDetails = groupDetails;
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
}
