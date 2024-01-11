package com.imag.master_groups.service;

import com.imag.master_groups.dto.MasterPrivilegeDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.MasterPrivilege;
import com.imag.master_groups.repository.MasterPrivilegeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MasterPrivilegeService {

    public static Integer idValue;

    @Autowired
    private MasterPrivilegeDao privilegesDao;

    @Transactional
    public MasterPrivilege addMasterPrivilege(MasterPrivilege masterPrivilege) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<MasterPrivilege> list = privilegesDao.findAll();
        if (!list.isEmpty()) {
            String privilegeId = list.stream().sorted(Comparator.comparing(MasterPrivilege::getPrivilegeId)
                    .reversed()).findFirst().get().getPrivilegeId();
            MasterPrivilegeService.idValue = Integer.parseInt(privilegeId.substring(5));
        }
        MasterPrivilegeService.idValue = 0;
        masterPrivilege.setPrivilegeId((("prvg_") + ++MasterPrivilegeService.idValue));
        masterPrivilege.setPrivileges(masterPrivilege.getPrivileges());
        masterPrivilege.setCreatedBy(authentication.getName());
        masterPrivilege.setCreatedAt(Timestamp.from(Instant.now()));
        masterPrivilege.setUpdatedBy(authentication.getName());
        masterPrivilege.setUpdatedAt(Timestamp.from(Instant.now()));
        masterPrivilege.setStatus(1);
        masterPrivilege.setDeletedAt(null);
        return privilegesDao.save(masterPrivilege);
    }

    @Transactional
    public MasterPrivilege updateMasterPrivilege(String privilegeId, MasterPrivilegeDto privilegeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<MasterPrivilege> optionalMasterPrivilege = privilegesDao.findById(privilegeId);
        if (optionalMasterPrivilege.isEmpty())
            throw new UserDefinedException("Invalid Privilege id");
        MasterPrivilege masterPrivilege = optionalMasterPrivilege.get();
        if (privilegeDto.getPrivileges() != null && !privilegeDto.getPrivileges().isEmpty()) {
            masterPrivilege.setPrivileges(privilegeDto.getPrivileges());
        }
        masterPrivilege.setRoleSpecificEnable(privilegeDto.isRoleSpecificEnable());
        masterPrivilege.setUserSpecificEnable(privilegeDto.isUserSpecificEnable());
        masterPrivilege.setUpdatedBy(authentication.getName());
        masterPrivilege.setUpdatedAt(Timestamp.from(Instant.now()));
        return privilegesDao.save(masterPrivilege);
    }

    public List<MasterPrivilege> getMasterPrivileges() {
        List<MasterPrivilege> masterPrivileges = privilegesDao.findByStatus(1);
        if (masterPrivileges.isEmpty())
            throw new UserDefinedException("There is no active privileges...!");
        return masterPrivileges;
    }

    public MasterPrivilege getMasterPrivilegeByPrivilegeId(String privilegeId) {
        Optional<MasterPrivilege> optionalMasterPrivilege = privilegesDao.findById(privilegeId);
        if (!optionalMasterPrivilege.isEmpty() && optionalMasterPrivilege.get().getStatus() == 1)
            return optionalMasterPrivilege.get();
        else
            throw new UserDefinedException("Invalid Privilege id");
    }

    @Transactional
    public String softDeleteMasterPrivilege(String privilegeId) {
        Optional<MasterPrivilege> optionalMasterPrivilege = privilegesDao.findById(privilegeId);
        if (optionalMasterPrivilege.isEmpty() || optionalMasterPrivilege.get().getStatus() == 0)
            throw new UserDefinedException("Invalid Privilege id");
        MasterPrivilege masterPrivilege = optionalMasterPrivilege.get();
        masterPrivilege.setStatus(0);
        masterPrivilege.setDeletedAt(Timestamp.from(Instant.now()));
        privilegesDao.save(masterPrivilege);
        return "Privilege Deleted temporarily..!";
    }

    @Transactional
    public String hardDeleteMasterPrivilege(String privilegeId) {
        Optional<MasterPrivilege> optionalMasterPrivilege = privilegesDao.findById(privilegeId);
        if (optionalMasterPrivilege.isEmpty())
            throw new UserDefinedException("Invalid Privilege id");
        privilegesDao.delete(optionalMasterPrivilege.get());
        return "Privilege Deleted Permanently..!";
    }
}
