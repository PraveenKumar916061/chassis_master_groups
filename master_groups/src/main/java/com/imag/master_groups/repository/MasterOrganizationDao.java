package com.imag.master_groups.repository;

import com.imag.master_groups.model.MasterOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterOrganizationDao extends JpaRepository<MasterOrganization, String> {
    List<MasterOrganization> findByStatus(Integer b);

    MasterOrganization findByOrgTypeName(String orgTypeName);

    boolean existsByOrgTypeName(String upperCase);
}
