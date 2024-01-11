package com.imag.master_groups.repository;

import com.imag.master_groups.model.MasterGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterGroupDao extends JpaRepository<MasterGroup, String> {
}
