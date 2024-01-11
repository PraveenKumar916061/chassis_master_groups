package com.imag.master_groups.service;


import com.imag.master_groups.dto.OrganizationMembersDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.*;
import com.imag.master_groups.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationMemberService {

    public static Integer idValue;
    @Autowired
    private OrganizationMemberDao orgMemberDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MasterOrganizationDao masterOrgDao;
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private MasterRoleDao masterRoleDao;
    @Autowired
    private MasterPrivilegeDao masterPrivilegeDao;

    @Transactional
    public OrganizationMember addOrganizationMember(OrganizationMember orgMember) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<OrganizationMember> list = orgMemberDao.findAll();
        if (!list.isEmpty()) {
            String orgMemberId = list.stream()
                    .sorted(Comparator.comparing(OrganizationMember::getOrgMemberId).reversed())
                    .findFirst().get().getOrgMemberId();
            OrganizationMemberService.idValue = Integer.parseInt(orgMemberId.substring(4));
        }
        OrganizationMemberService.idValue = 0;
        orgMember.setOrgMemberId((("mem_") + ++OrganizationMemberService.idValue));

        if (orgMember.getOrgId() == null || orgMember.getOrgId().isEmpty())
            throw new UserDefinedException("Organization id can't be null or empty");
        else {
            Optional<Organization> org = organizationDao.findById(orgMember.getOrgId());
            if (org.isEmpty()) throw new UserDefinedException("Invalid organization id...!");
            orgMember.setOrgId(orgMember.getOrgId());
        }

        if (orgMember.getUserId() == null || orgMember.getUserId().isEmpty())
            throw new UserDefinedException("User id can't be null or empty");
        else {
            Optional<User> user = userDao.findById(orgMember.getUserId());
            if (user.isEmpty()) throw new UserDefinedException("Invalid user id....!");
            orgMember.setUserId(orgMember.getUserId());
        }

        if (orgMember.getRoleId() == null || orgMember.getRoleId().isEmpty())
            throw new UserDefinedException("Role id can't be null or empty");
        else {
            Optional<MasterRole> masterRole = masterRoleDao.findById(orgMember.getRoleId());
            if (masterRole.isEmpty()) throw new UserDefinedException("Invalid role id....!");
            orgMember.setRoleId(orgMember.getRoleId());
        }

        if (orgMember.getPrivilegeId() == null || orgMember.getPrivilegeId().isEmpty())
            throw new UserDefinedException("Privilege id can't be null or empty");
        else {
            Optional<MasterPrivilege> optionalMasterPrivilege = masterPrivilegeDao.findById(orgMember.getPrivilegeId());
            if (optionalMasterPrivilege.isEmpty()) throw new UserDefinedException("Invalid Privilege id...!");
            orgMember.setPrivilegeId(orgMember.getPrivilegeId());
        }
        orgMember.setCreatedBy(authentication.getName());
        orgMember.setUpdatedBy(authentication.getName());
        orgMember.setCreatedAt(Timestamp.from(Instant.now()));
        orgMember.setUpdatedAt(Timestamp.from(Instant.now()));
        orgMember.setDeletedAt(null);
        orgMember.setStatus(1);
        return orgMemberDao.save(orgMember);
    }

    @Transactional
    public OrganizationMember updateOrgMember(String id, OrganizationMembersDto orgMemDto) throws UserDefinedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<OrganizationMember> orgMember = orgMemberDao.findById(id);
        if (orgMember.isEmpty()) {
            throw new UserDefinedException("Invalid Organization Member id...!");
        }
        OrganizationMember organizationMember = orgMember.get();
        if (orgMemDto.getOrgId() != null && !orgMemDto.getOrgId().isEmpty()) {
            Optional<Organization> org = organizationDao.findById(orgMemDto.getOrgId());
            if (org.isEmpty())
                throw new UserDefinedException("Invalid Organization id...!");
            organizationMember.setOrgId(orgMemDto.getOrgId());
        }
        if (orgMemDto.getUserId() != null && !orgMemDto.getUserId().isEmpty()) {
            Optional<User> user = userDao.findById(orgMemDto.getUserId());
            if (user.isEmpty())
                throw new UserDefinedException("Invalid User id...!");
            organizationMember.setUserId(orgMemDto.getUserId());
        }
        if (orgMemDto.getRoleId() != null && !orgMemDto.getRoleId().isEmpty()) {
            Optional<MasterRole> masterRoles = masterRoleDao.findById(orgMemDto.getRoleId());
            if (masterRoles.isEmpty())
                throw new UserDefinedException("Invalid Role id....!");
            organizationMember.setRoleId(orgMemDto.getRoleId());
        }
        if (orgMemDto.getPrivilegeId() != null && !orgMemDto.getPrivilegeId().isEmpty()) {
            Optional<MasterPrivilege> optionalMasterPrivilege = masterPrivilegeDao.findById(orgMemDto.getPrivilegeId());
            if (optionalMasterPrivilege.isEmpty())
                throw new UserDefinedException("Invalid Privilege id....!");
            organizationMember.setPrivilegeId(orgMemDto.getPrivilegeId());
        }
        organizationMember.setUpdatedBy(authentication.getName());
        organizationMember.setUpdatedAt(Timestamp.from(Instant.now()));
        return orgMemberDao.save(organizationMember);
    }

    public List<OrganizationMember> getOrganizationMembers() {
        List<OrganizationMember> orgMembers = orgMemberDao.findAll();
        if (orgMembers.isEmpty())
            throw new UserDefinedException("There is no data in the table...!");
        return orgMembers;
    }

    public List<OrganizationMember> getOrgMembers() {
        List<OrganizationMember> orgMembers = orgMemberDao.findByStatus(1);
        if (orgMembers.isEmpty())
            throw new UserDefinedException("There is no active members...!");
        return orgMembers;
    }

    @Transactional
    public String deleteOrgMem(String id) throws UserDefinedException {
        Optional<OrganizationMember> optionalOrgMember = orgMemberDao.findById(id);
        if (optionalOrgMember.isEmpty()) {
            throw new UserDefinedException("Invalid organization member id...!");
        }
        orgMemberDao.delete(optionalOrgMember.get());
        return id + " : Organization member deleted permanently....!";
    }

    @Transactional
    public String removeOrgMem(String id) throws UserDefinedException {
        Optional<OrganizationMember> optionalOrgMember = orgMemberDao.findById(id);
        if (optionalOrgMember.isEmpty() || optionalOrgMember.get().getStatus() == 0) {
            throw new UserDefinedException("Invalid organization member id....!");
        }
        OrganizationMember organizationMember = optionalOrgMember.get();
        organizationMember.setStatus(0);
        organizationMember.setDeletedAt(Timestamp.from(Instant.now()));
        orgMemberDao.save(organizationMember);
        return id + " : Organization Member deleted successfully";
    }

    public OrganizationMember organizationMember(String name) {
        return orgMemberDao.findByUserId(getUserId(name));
    }

    public String getUserId(String name) {
        Optional<User> optionalUser = userDao.findByEmail(name);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("Username not found");
        User user = optionalUser.get();
        return user.getUserId();
    }
}
