package com.imag.master_groups.webcontroller;

import com.imag.master_groups.dto.MasterOrganizationDto;
import com.imag.master_groups.model.MasterOrganization;
import com.imag.master_groups.service.MasterOrganizationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MasterOrganizationController {

    @Autowired
    private MasterOrganizationService masterOrgService;

    @GetMapping("/master-organizations")
    public List<MasterOrganization> allRecords() {
        return masterOrgService.allDetails();
    }

    @PostMapping("/add-master-organization")
    public MasterOrganization addingRecord(@Valid @RequestBody MasterOrganization masterOrganization) throws Exception {
        return masterOrgService.addMasterOrganization(masterOrganization);
    }

    @DeleteMapping("/softDelete-master-organization/{id}")
    public String deleteRecord(@PathVariable("id") String id) {
        return masterOrgService.remove(id.toUpperCase());
    }

    @PutMapping("/update-master-organization/{orgId}")
    public MasterOrganization recordUpdate(@PathVariable("orgId") @NotNull String orgId, @RequestBody MasterOrganizationDto masterOrgDto) {
        return masterOrgService.updateMasterOrganization(orgId.toUpperCase(), masterOrgDto);
    }

    @GetMapping("/active-master-organizations")
    public List<MasterOrganization> masterOrgList() {
        return masterOrgService.masterOrgList();
    }

    @DeleteMapping("/permanent-delete-master-organization/{id}")
    public String permanentDelete(@RequestParam("id") String id) {
        return masterOrgService.delete(id.toUpperCase());
    }
}
