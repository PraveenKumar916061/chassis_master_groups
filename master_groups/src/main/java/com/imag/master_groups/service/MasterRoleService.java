package com.imag.master_groups.service;

import com.imag.master_groups.dto.MasterRoleDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.MasterRole;
import com.imag.master_groups.repository.MasterRoleDao;
import com.imag.master_groups.utils.Validation;
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
public class MasterRoleService {
    public static Integer idValue;
    @Autowired
    private MasterRoleDao masterRoleDao;

    @Transactional
    public MasterRole addRole(MasterRole masterRole) throws UserDefinedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<MasterRole> list = masterRoleDao.findAll();
        if (!list.isEmpty()) {
            String roleId = list.stream().sorted(Comparator.comparing(MasterRole::getRoleId)
                    .reversed()).findFirst().get().getRoleId();
            MasterRoleService.idValue = Integer.parseInt(roleId.substring(5));
        }
        MasterRoleService.idValue =0;
        masterRole.setRoleId((("role_") + ++MasterRoleService.idValue));
        Optional<MasterRole> optionalMasterRole = masterRoleDao.findByRoleName(masterRole.getRoleName());
        if (!optionalMasterRole.isEmpty()) {
            throw new UserDefinedException("Role is already exists...!");
        }
        masterRole.setRoleName(Validation.nameValidation(masterRole.getRoleName()));
        masterRole.setRoleDescription(Validation.descriptionValidation(masterRole.getRoleDescription()));
        masterRole.setCreatedBy(authentication.getName());
        masterRole.setUpdatedBy(authentication.getName());
        masterRole.setStatus(1);
        masterRole.setCreatedAt(Timestamp.from(Instant.now()));
        masterRole.setUpdatedAt(Timestamp.from(Instant.now()));
        masterRole.setDeletedAt(null);
        return masterRoleDao.save(masterRole);
    }

    @Transactional
    public MasterRole updateRoleDetails(String roleId, MasterRoleDto masterRoleDto) throws UserDefinedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<MasterRole> optionalMasterRole = masterRoleDao.findById(roleId.toUpperCase());
        if (optionalMasterRole.isEmpty()) {
            throw new UserDefinedException("Role id is not found");
        }
        MasterRole masterRole = optionalMasterRole.get();
        if (masterRoleDto.getRoleName() != null && !masterRoleDto.getRoleName().isEmpty()) {
            Optional<MasterRole> optionalMasterRole1 = masterRoleDao.findByRoleName(masterRoleDto.getRoleName().toUpperCase());
            if (!optionalMasterRole1.isEmpty())
                throw new UserDefinedException("Role is already exists...!");
            masterRole.setRoleName(Validation.nameValidation(masterRoleDto.getRoleName()));
        }
        if (masterRoleDto.getRoleDescription() != null && !masterRoleDto.getRoleDescription().isEmpty()) {
            masterRole.setRoleDescription(Validation.descriptionValidation(masterRoleDto.getRoleDescription()));
        }
        masterRole.setUpdatedBy(authentication.getName());
        masterRole.setUpdatedAt(Timestamp.from(Instant.now()));
        return masterRoleDao.save(masterRole);
    }

    public List<MasterRole> getRoles() throws UserDefinedException {
        List<MasterRole> masterRoles = masterRoleDao.findByStatus(1);
        if (masterRoles.isEmpty()) throw new UserDefinedException("There is no data in roles table");
        return masterRoles;
    }

    @Transactional
    public String removeRole(String roleId) throws UserDefinedException {
        Optional<MasterRole> optionalMasterRole = masterRoleDao.findById(roleId.toUpperCase());
        if (optionalMasterRole.isEmpty() || optionalMasterRole.get().getStatus() == 0)
            throw new UserDefinedException("Role id is not found");
        MasterRole masterRole = optionalMasterRole.get();
        masterRole.setStatus(0);
        masterRole.setDeletedAt(Timestamp.from(Instant.now()));
        masterRoleDao.save(masterRole);
        return "Master role deleted Temporarily....!";
    }

    @Transactional
    public String deleteRole(String roleId) throws UserDefinedException {
        Optional<MasterRole> optionalMasterRole = masterRoleDao.findById(roleId.toUpperCase());
        if (optionalMasterRole.isEmpty())
            throw new UserDefinedException("role is not found");
        masterRoleDao.delete(optionalMasterRole.get());
        return "Master role deleted Permanently....!";
    }
}
