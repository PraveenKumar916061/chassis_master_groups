package com.imag.master_groups.repository;

import com.imag.master_groups.model.MasterRole;
import com.imag.master_groups.model.OrganizationMember;
import com.imag.master_groups.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrganizationMemberDao extends JpaRepository<OrganizationMember,String> {

    List<OrganizationMember> findByStatus(int i);

    OrganizationMember findByUserId(String userId);

    List<MasterRole> findByMasterRoleRoleIdIn(List<String> roleIds);

    @Query(name = "select role_id from organization_members where usr_id=:userId ",nativeQuery = true)
    List<String> findUserRoleByUserId(@Param("userId") String userId);

}
