package com.imag.master_groups.service;

import com.google.gson.Gson;
import com.imag.master_groups.dto.MasterGroupResponse;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.GroupDetails;
import com.imag.master_groups.model.MasterGroup;
import com.imag.master_groups.model.User;
import com.imag.master_groups.repository.MasterGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MasterGroupService {

    @Autowired
    private MasterGroupDao masterGroupDao;

    @Transactional
    public MasterGroup addMasterGroup(GroupDetails groupDetails) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Gson g = new Gson();
        String groupDetails1 = g.toJson(groupDetails);
        MasterGroup masterGroup = new MasterGroup();
        masterGroup.setGroupId(groupDetails.getGroupId());
        masterGroup.setGroupDetails(groupDetails1);
        masterGroup.setCreatedBy(user.getFirstName());
        masterGroup.setCreatedAt(Timestamp.from(Instant.now()));
        masterGroup.setUpdatedBy(user.getFirstName());
        masterGroup.setUpdatedAt(Timestamp.from(Instant.now()));
        return masterGroupDao.save(masterGroup);
    }

    public List<MasterGroupResponse> list() {
        List<MasterGroup> masterGroups = masterGroupDao.findAll();
        List<MasterGroupResponse> list = new ArrayList<>();
        for (MasterGroup group : masterGroups) {
            MasterGroupResponse groupResponse = new MasterGroupResponse();
            groupResponse.setGroupId(group.getGroupId());
            Gson gson = new Gson();
            GroupDetails groupDetails = gson.fromJson(group.getGroupDetails(), GroupDetails.class);
            groupResponse.setGroupDetails(groupDetails);
            groupResponse.setCreatedBy(group.getCreatedBy());
            groupResponse.setCreatedAt(group.getCreatedAt());
            groupResponse.setUpdatedBy(group.getUpdatedBy());
            groupResponse.setUpdatedAt(group.getUpdatedAt());
            list.add(groupResponse);
        }
        return list;
    }

    @Transactional
    public MasterGroup updateMasterGroup(String groupId, GroupDetails groupDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<MasterGroup> optionalMasterGroup = masterGroupDao.findById(groupId);
        if (optionalMasterGroup.isEmpty()) {
            throw new UserDefinedException("Invalid group id....! Pls check once :)");
        }
        MasterGroup masterGroup = optionalMasterGroup.get();
        masterGroup.setGroupDetails(new Gson().toJson(groupDetails));
        masterGroup.setUpdatedBy(authentication.getName());
        masterGroup.setUpdatedAt(Timestamp.from(Instant.now()));
        return masterGroupDao.save(masterGroup);
    }

    public GroupDetails getMasterGroupByGroupId(String groupId) {
        Optional<MasterGroup> optionalMasterGroup = masterGroupDao.findById(groupId);
        if (optionalMasterGroup.isEmpty()) {
            throw new UserDefinedException("Invalid group id....! Pls check it once :)");
        }
        MasterGroup masterGroup = optionalMasterGroup.get();
        Gson gson = new Gson();
        GroupDetails groupDetails = gson.fromJson(masterGroup.getGroupDetails(), GroupDetails.class);
        return groupDetails;
    }
}
