package com.imag.master_groups.webcontroller;

import com.imag.master_groups.dto.MasterRoleDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.MasterRole;
import com.imag.master_groups.service.MasterRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MasterRoleController {
    @Autowired
    private MasterRoleService masterRoleService;

    @PostMapping("/add-role")
    public MasterRole addRole(@RequestBody MasterRole masterRole) throws UserDefinedException {
        return masterRoleService.addRole(masterRole);
    }

    @PutMapping("update-details/{role-id}")
    public MasterRole updateRole(@PathVariable("role-id") String roleId, @RequestBody MasterRoleDto masterRoleDto) throws UserDefinedException {
        return masterRoleService.updateRoleDetails(roleId.toUpperCase(), masterRoleDto);
    }

    @GetMapping("/roles")
    public List<MasterRole> getRoles() throws UserDefinedException {
        return masterRoleService.getRoles();
    }

    @DeleteMapping("remove-role/{role-id}")
    public String removeRole(@PathVariable("role-id") String roleId) throws UserDefinedException {
        return masterRoleService.removeRole(roleId.toUpperCase());
    }

    @DeleteMapping("/delete-role/{role-id}")
    public String deleteRole(@PathVariable("role-id") String roleId) throws UserDefinedException {
        return masterRoleService.deleteRole(roleId.toUpperCase());
    }
}
