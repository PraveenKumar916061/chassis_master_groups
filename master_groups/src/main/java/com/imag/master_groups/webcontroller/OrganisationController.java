package com.imag.master_groups.webcontroller;


import com.imag.master_groups.dto.OrganizationDto;
import com.imag.master_groups.dto.OrganizationRequestDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.Organization;
import com.imag.master_groups.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganisationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/org")
    public Organization addOrganisation(@RequestBody OrganizationRequestDto organization) throws Exception {
       return organizationService.addOrganization(organization);
    }

    @GetMapping("/active-organizations")
    public List<Organization> getOrganisation(){
        return organizationService.getOrganization();
    }

    @GetMapping("/organizations")
    public List<Organization> getAllOrganizations(){
        return organizationService.getAllOrganizations();
    }

    @DeleteMapping("/delete-organization/{id}")
    public String deleteOrganisation(@PathVariable("id")String id) throws Exception {
        return organizationService.deleteOrganization(id.toUpperCase());
    }

    @PutMapping("/update-organization/{id}")
    public Organization updateOrganisation(@PathVariable("id") String id, @RequestBody OrganizationDto organizationDto) throws Exception {
            return organizationService.updateOrganization(id.toUpperCase(),organizationDto);
    }

    @DeleteMapping("/remove-organization/{id}")
    private String softDeleteOrganization(@PathVariable String id) throws UserDefinedException {
      return   organizationService.softDeleteOrganization(id.toUpperCase());
    }


}


