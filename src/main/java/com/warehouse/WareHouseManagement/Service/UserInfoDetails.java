package com.warehouse.WareHouseManagement.Service;


import com.warehouse.WareHouseManagement.Entity.Role;
import com.warehouse.WareHouseManagement.Entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {
    private final Users user;

    public UserInfoDetails(Users user ){
        this.user=user;
    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {


//        List<GrantedAuthority> authorities=new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(user.getRoles()));
//        return authorities;


//        List<GrantedAuthority> authorities = new ArrayList<>();
//        String roles = user.getRoles();
//        if (roles != null && !roles.isEmpty()) {
//            String[] roleArray = roles.split(",");
//            for (String role : roleArray) {
//                authorities.add(new SimpleGrantedAuthority(role.trim()));
//            }
//        }
//        return authorities;
//    }
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    List<Role> roles = user.getRoles();
    for (Role role : roles) {
        authorities.add(new SimpleGrantedAuthority(role.name()));
    }
    return authorities;
}
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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