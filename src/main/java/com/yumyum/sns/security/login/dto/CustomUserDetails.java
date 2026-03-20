package com.yumyum.sns.security.login.dto;

import com.yumyum.sns.member.entity.Member;
import com.yumyum.sns.security.common.AuthMember;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails, AuthMember, Principal {

    //private final Member member;
    private final Long userId;
    private final String identifier;
    private final String password;
    private final String role;

    public CustomUserDetails(Member member) {
        this.userId = member.getId();
        this.identifier = member.getIdentifier();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

    public CustomUserDetails(Long userId, String username, String role) {
        this.role = role;
        this.identifier = username;
        this.userId = userId;
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.role);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.identifier;
    }

    @Override
    public String getName() {
        return this.identifier;
    }
    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }


}
