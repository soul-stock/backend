package com.soulstock.backend.security.dto;

import com.soulstock.backend.domain.member.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserDetailsImpl implements UserDetails {

    private Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    // 권한 생성
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = member.getRole().name();
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // 계정 상태 관련 메서드
    @Override
    public boolean isEnabled() { return true; }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
}