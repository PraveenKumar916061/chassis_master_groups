package com.imag.master_groups.model;

import com.google.gson.Gson;
import com.imag.master_groups.repository.MasterGroupDao;
import com.imag.master_groups.utils.Validation;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class GroupDetails {

    @Autowired
    MasterGroupDao masterGroupDao;

    private String groupId;
    private String groupName;
    private String groupDescription;
    private List<GroupDetails> subGroups;

    public static Integer idValue = 0;

    @PreUpdate
    public void set() {
        List<MasterGroup> masterGroups = masterGroupDao.findAll();
        List<GroupDetails> groupDetails = new ArrayList<>();
        for (MasterGroup group : masterGroups) {
            Gson gson = new Gson();
            GroupDetails groupDetails1 = gson.fromJson(group.getGroupDetails(), GroupDetails.class);
            groupDetails.add(groupDetails1);
        }
        String groupId = groupDetails.stream()
                .sorted(Comparator.comparing(GroupDetails::getGroupId).reversed())
                .findFirst().get().getGroupId();
        GroupDetails.idValue = Integer.parseInt(groupId.substring(4));
    }

    public void setGroupId(String groupId) {
        this.groupId = (("grp_") + ++GroupDetails.idValue).toUpperCase();
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName.toUpperCase();
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = Validation.descriptionValidation(groupDescription).toUpperCase();
    }

    public void setSubGroups(List<GroupDetails> subGroups) {
        this.subGroups = subGroups;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public List<GroupDetails> getSubGroups() {
        return subGroups;
    }
}
