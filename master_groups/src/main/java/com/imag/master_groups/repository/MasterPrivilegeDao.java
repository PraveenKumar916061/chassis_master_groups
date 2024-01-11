package com.imag.master_groups.repository;

import com.imag.master_groups.model.MasterPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterPrivilegeDao extends JpaRepository<MasterPrivilege, String> {

    List<MasterPrivilege> findByStatus(Integer status);
}
