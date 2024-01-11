package com.imag.master_groups.webcontroller;

import com.imag.master_groups.dto.MasterGroupResponse;
import com.imag.master_groups.model.GroupDetails;
import com.imag.master_groups.model.MasterGroup;
import com.imag.master_groups.service.MasterGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/master-group")
public class MasterGroupController {

    private MasterGroupService groupService;

    public MasterGroupController(MasterGroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/register-master-group")
    public MasterGroup addMasterGroup(@RequestBody GroupDetails groupDetails) {
        return groupService.addMasterGroup(groupDetails);
    }

    @GetMapping("/master-groups")
    public List<MasterGroupResponse> getMasterGroups() {
        return groupService.list();
    }

    @PutMapping("/update-master-group/{groupId}")
    public MasterGroup updateMasterGroup(@PathVariable String groupId, @RequestBody GroupDetails groupDetails) {
        return groupService.updateMasterGroup(groupId.toUpperCase(), groupDetails);
    }

    @GetMapping("/group-details-by-id/{groupId}")
    public GroupDetails getMasterGroup(@PathVariable String groupId) {
        return groupService.getMasterGroupByGroupId(groupId.toUpperCase());
    }
}
