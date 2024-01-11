package com.imag.master_groups.security;

import com.imag.master_groups.model.MasterRole;
import com.imag.master_groups.model.User;
import com.imag.master_groups.repository.OrganizationMemberDao;
import com.imag.master_groups.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private UserDao userDao;

    @Autowired
    private OrganizationMemberDao orgMemberDao;

    private String userName;
    private String password;

    public CustomUserDetails(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        User user = userDao.findByUserNameAndPassword(userName, password);
//        System.out.println(userDao);
//        List<String> roleIds = orgMemberDao.findUserRoleByUserId(user.getUserId());
//        List<MasterRole> masterRoles = orgMemberDao.findByMasterRoleRoleIdIn(roleIds);
//        return masterRoles.stream().map(MasterRole::getRoleName)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet());
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
