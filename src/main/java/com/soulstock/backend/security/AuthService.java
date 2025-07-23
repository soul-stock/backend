package com.soulstock.backend.security;

import com.soulstock.backend.domain.member.MemberService;
import com.soulstock.backend.domain.member.entity.Member;
import com.soulstock.backend.security.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {
        Member member = toMapping(userDto);
        memberService.validateBeforeRegister(member);
        memberService.registerMember(member);
    }

    private Member toMapping(UserDto userDto) {
        return Member.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .nickname(userDto.getNickname())
                .build();
    }

    public Optional<UserDto> findByUsername(String username) {
        Member member = memberService.findByUsername(username);
        return Optional.ofNullable(toDto(member));
    }

    private UserDto toDto(Member member) {
        return UserDto.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }

    public boolean validatePassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}