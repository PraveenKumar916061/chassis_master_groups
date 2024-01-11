package com.imag.master_groups.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "usr_id")
    private String userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "pwd")
    private String password;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "usr_created_date")
    private Timestamp userCreatedAt;
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

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private OrganizationMember organizationMember;

    public void setUserId(String userId) {
        this.userId = userId.toUpperCase();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.toUpperCase();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.toUpperCase();
    }

    public void setEmail(String email) {
        this.email = email.toUpperCase();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address.toUpperCase();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.toUpperCase();
    }

    public void setUserCreatedAt(Timestamp userCreatedAt) {
        this.userCreatedAt = userCreatedAt;
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

    public void setOrganizationMember(OrganizationMember organizationMember) {
        this.organizationMember = organizationMember;
    }
}
