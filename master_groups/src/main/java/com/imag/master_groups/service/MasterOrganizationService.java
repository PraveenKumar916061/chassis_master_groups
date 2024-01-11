package com.imag.master_groups.service;

import com.imag.master_groups.dto.MasterOrganizationDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.MasterOrganization;
import com.imag.master_groups.repository.MasterOrganizationDao;
import com.imag.master_groups.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MasterOrganizationService {
    private static Integer idValue = 0;
    @Autowired
    private MasterOrganizationDao masterOrgDao;

    public List<MasterOrganization> allDetails() throws UserDefinedException {
        List<MasterOrganization> list = masterOrgDao.findAll();
        if (list.isEmpty()) throw new UserDefinedException("No data in table");
        return list;
    }

    public List<MasterOrganization> masterOrgList() throws UserDefinedException {
        List<MasterOrganization> list = masterOrgDao.findByStatus(1);
        if (list.isEmpty()) throw new UserDefinedException("There are no active master organizations");
        return list;
    }

    @Transactional
    public MasterOrganization addMasterOrganization(MasterOrganization masterOrg) throws UserDefinedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<MasterOrganization> masterOrganizations = masterOrgDao.findAll();
        if (!masterOrganizations.isEmpty()) {
            String orgTypeId = masterOrganizations.stream().sorted(Comparator.comparing(MasterOrganization::getOrgTypeId).reversed()).findFirst().get().getOrgTypeId();
            MasterOrganizationService.idValue = Integer.parseInt(orgTypeId.substring(7));
        }
        MasterOrganizationService.idValue = 0;
        masterOrg.setOrgTypeId((("org_typ") + ++MasterOrganizationService.idValue));

        if (!masterOrgDao.existsByOrgTypeName(masterOrg.getOrgTypeName().toUpperCase())) {
            masterOrg.setOrgTypeName(Validation.nameValidation(masterOrg.getOrgTypeName()));
        } else throw new UserDefinedException("Master organization is already exist...!");
        masterOrg.setOrgDescription(Validation.descriptionValidation(masterOrg.getOrgDescription()));
        masterOrg.setCreatedBy(authentication.getName());
        masterOrg.setCreatedAt(LocalDateTime.now());
        masterOrg.setUpdatedBy(authentication.getName());
        masterOrg.setUpdatedAt(LocalDateTime.now());
        masterOrg.setStatus(1);
        masterOrg.setDeletedAt(null);
        return masterOrgDao.save(masterOrg);
    }

    @Transactional
    public String remove(String id) throws UserDefinedException {
        Optional<MasterOrganization> optionalMasterOrg = masterOrgDao.findById(id);
        if (optionalMasterOrg.isEmpty()) throw new UserDefinedException("Invalid master organization id....!");
        MasterOrganization masterOrganization = optionalMasterOrg.get();
        masterOrganization.setDeletedAt(LocalDateTime.now());
        masterOrganization.setStatus(0);
        masterOrgDao.save(masterOrganization);
        return " Removed Successfully.....!";
    }

    @Transactional
    public String delete(String id) throws UserDefinedException {
        Optional<MasterOrganization> optionalMasterOrg = masterOrgDao.findById(id);
        if (optionalMasterOrg.isEmpty()) throw new UserDefinedException("Invalid master organization id ");
        masterOrgDao.deleteById(id);
        return " Deleted successfully...! ";
    }

    @Transactional
    public MasterOrganization updateMasterOrganization(String orgId, MasterOrganizationDto masterOrgDto) throws UserDefinedException {
        Optional<MasterOrganization> optionalMasterOrg = masterOrgDao.findById(orgId);
        if (optionalMasterOrg.isEmpty()) throw new UserDefinedException("Invalid master organization id....!");

        MasterOrganization masterOrg = optionalMasterOrg.get();
        if (masterOrgDto.getOrgName() != null && !masterOrgDto.getOrgName().isEmpty()) {
            validateOrgNameUniqueness(masterOrgDto.getOrgName(), masterOrg.getOrgTypeName());
            masterOrg.setOrgTypeName(Validation.nameValidation(masterOrgDto.getOrgName()));
        }
        if (masterOrgDto.getOrgDescription() != null && !masterOrgDto.getOrgDescription().isEmpty())
            masterOrg.setOrgDescription(Validation.descriptionValidation(masterOrgDto.getOrgDescription()));
        return masterOrgDao.save(masterOrg);
    }

    private void validateOrgNameUniqueness(String orgName, String currentOrgTypeName) throws UserDefinedException {
        if (orgName != null && !orgName.equalsIgnoreCase(currentOrgTypeName))
            if (masterOrgDao.existsByOrgTypeName(orgName.toUpperCase())) {
                throw new UserDefinedException("orgName is already exists");
            }
    }
}