package com.imag.master_groups.repository;

import com.imag.master_groups.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationDao extends JpaRepository<Organization, String> {
    Optional<Organization> findByOrgName(String orgName);

    List<Organization> findByStatus(int i);
}
