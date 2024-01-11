package com.imag.master_groups.webcontroller;

import com.imag.master_groups.dto.MasterPrivilegeDto;
import com.imag.master_groups.model.MasterPrivilege;
import com.imag.master_groups.service.MasterPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/master-privilege")
public class MasterPrivilegeController {

    @Autowired
    private MasterPrivilegeService privilegeService;

    @PostMapping("/add-privilege")
    public ResponseEntity addPrivilege(@RequestBody MasterPrivilege masterPrivilege) {
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.addMasterPrivilege(masterPrivilege));
    }

    @PutMapping("/update-privilege/{privilegeId}")
    public ResponseEntity updatePrivilege(@PathVariable String privilegeId, @RequestBody MasterPrivilegeDto privilegeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.updateMasterPrivilege(privilegeId.toUpperCase(), privilegeDto));
    }

    @GetMapping("/privileges")
    public ResponseEntity privileges() {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.getMasterPrivileges());
    }

    @GetMapping("/privilege-by-id/{privilegeId}")
    public ResponseEntity privilege(@PathVariable String privilegeId) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.getMasterPrivilegeByPrivilegeId(privilegeId.toUpperCase()));
    }

    @DeleteMapping("/privilege-softDelete/{privilegeId}")
    public ResponseEntity privilegeSoftDelete(@PathVariable String privilegeId) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.softDeleteMasterPrivilege(privilegeId.toUpperCase()));
    }

    @DeleteMapping("/privilege-hardDelete/{privilegeId}")
    public ResponseEntity privilegeHardDelete(@PathVariable String privilegeId) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.hardDeleteMasterPrivilege(privilegeId.toUpperCase()));
    }
}
