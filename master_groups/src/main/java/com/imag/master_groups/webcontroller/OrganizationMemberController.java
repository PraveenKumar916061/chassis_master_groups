package com.imag.master_groups.webcontroller;

import com.imag.master_groups.dto.OrganizationMembersDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.OrganizationMember;
import com.imag.master_groups.service.OrganizationMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationMemberController {


    @Autowired
    private OrganizationMemberService orgMemService;


    @PostMapping("/org-members")
    public OrganizationMember addOrgMembers(@RequestBody OrganizationMember orgMembers) {
        return orgMemService.addOrganizationMember(orgMembers);
    }

    @GetMapping("/org-members/all")
    public List<OrganizationMember> getAllOrganisationMembers() {
        return orgMemService.getOrganizationMembers();
    }

    @GetMapping("/org-members/active")
    public List<OrganizationMember> getOrganizationMembers() {
        return orgMemService.getOrgMembers();
    }

    @DeleteMapping("/org-members/{id}")
    public String deleteOrgMember(@PathVariable("id") String id) throws UserDefinedException {
        return orgMemService.deleteOrgMem(id.toUpperCase());
    }

    @DeleteMapping("/soft/org-members/{id}")
    public String removeOrgMember(@PathVariable("id") String id) throws UserDefinedException {
        return orgMemService.removeOrgMem(id.toUpperCase());
    }

    @PutMapping("/org-mem/{id}")
    public OrganizationMember updateOrgMember(@PathVariable("id") String id, @RequestBody OrganizationMembersDto orgMemDto) throws UserDefinedException {
        return orgMemService.updateOrgMember(id.toUpperCase(), orgMemDto);
    }
}
