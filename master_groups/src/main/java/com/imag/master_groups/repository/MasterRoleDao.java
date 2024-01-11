package com.imag.master_groups.repository;

import com.imag.master_groups.model.MasterRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRoleDao extends JpaRepository<MasterRole,String> {
    List<MasterRole> findByStatus(int i);

    Optional<MasterRole> findByRoleName(String roleName);
}

