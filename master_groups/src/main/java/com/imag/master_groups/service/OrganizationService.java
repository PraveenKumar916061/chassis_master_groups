package com.imag.master_groups.service;


import com.imag.master_groups.dto.OrganizationDto;
import com.imag.master_groups.dto.OrganizationRequestDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.MasterOrganization;
import com.imag.master_groups.model.Organization;
import com.imag.master_groups.repository.MasterOrganizationDao;
import com.imag.master_groups.repository.OrganizationDao;
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
public class OrganizationService {

    public static Integer idValue;

    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private MasterOrganizationDao masterOrganizationDao;

    @Transactional
    public Organization addOrganization(OrganizationRequestDto orgRequestDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Organization> optionalOrganization = organizationDao.findByOrgName(orgRequestDto.getOrgName().toUpperCase());
        if (optionalOrganization.isPresent()) {
            throw new UserDefinedException("organisation already exists...!");
        }
        Organization organization = new Organization();
        List<Organization> list = organizationDao.findAll();
        if (!list.isEmpty()) {
            String orgId = list.stream().sorted(Comparator.comparing(Organization::getOrgId).reversed()).findFirst().get().getOrgId();
            OrganizationService.idValue = Integer.parseInt(orgId.substring(4));
        }
        OrganizationService.idValue = 0;
        organization.setOrgId((("org_") + ++OrganizationService.idValue));
        organization.setOrgName(Validation.nameValidation(orgRequestDto.getOrgName()));
        organization.setOrgDesc(Validation.descriptionValidation(orgRequestDto.getOrgDesc()));
        if (orgRequestDto.getOrgTypeId() == null || orgRequestDto.getOrgTypeId().isEmpty()) {
            throw new UserDefinedException("organization type can't be null or empty");
        } else {
            Optional<MasterOrganization> optionalMasterOrg = masterOrganizationDao.findById(orgRequestDto.getOrgTypeId());
            if (optionalMasterOrg.isEmpty()) throw new UserDefinedException("Organization type doesn't exist..!");
            organization.setOrgTypId(orgRequestDto.getOrgTypeId());
        }
        organization.setCreatedBy(authentication.getName());
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedBy(authentication.getName());
        organization.setUpdatedAt(LocalDateTime.now());
        organization.setDeletedAt(null);
        organization.setStatus(1);
        return organizationDao.save(organization);
    }

    public List<Organization> getOrganization() {
        return organizationDao.findByStatus(1);
    }

    @Transactional
    public String deleteOrganization(String id) throws Exception {
        Optional<Organization> org = organizationDao.findById(id);
        if (org.isEmpty()) throw new UserDefinedException("Organization id not found...!");
        organizationDao.deleteById(id);
        return "Permanently deleted the Organisation : " + org.get().getOrgName();
    }

    @Transactional
    public Organization updateOrganization(String id, OrganizationDto organisationDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Organization> optionalOrganization = organizationDao.findById(id);
        if (optionalOrganization.isEmpty()) {
            throw new UserDefinedException("Organization id is not found.....!");
        }
        Organization organization = optionalOrganization.get();
        if (organisationDto.getOrgName() != null && !organisationDto.getOrgName().isEmpty()) {
            organization.setOrgName(Validation.nameValidation(organisationDto.getOrgName()));
        }
        if (organisationDto.getOrgDesc() != null && !organisationDto.getOrgDesc().isEmpty()) {
            organization.setOrgDesc(Validation.descriptionValidation(organisationDto.getOrgDesc()));
        }
        organization.setUpdatedBy(authentication.getName());
        organization.setUpdatedAt(LocalDateTime.now());
        return organizationDao.save(organization);
    }

    @Transactional
    public String softDeleteOrganization(String id) throws UserDefinedException {
        Optional<Organization> optionalOrganization = organizationDao.findById(id);
        if (optionalOrganization.isEmpty() || optionalOrganization.get().getStatus() == 0) {
            throw new UserDefinedException("Invalid organization id....!");
        }
        Organization organization = optionalOrganization.get();
        organization.setStatus(0);
        organization.setDeletedAt(LocalDateTime.now());
        organizationDao.save(organization);
        return "Organisation deleted successfully";
    }

    public List<Organization> getAllOrganizations() {
        return organizationDao.findAll();
    }
}
