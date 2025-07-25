package com.soulstock.backend.security;

import com.soulstock.backend.domain.member.MemberRepository;
import com.soulstock.backend.domain.member.entity.Member;
import com.soulstock.backend.security.Service.AuthService;
import com.soulstock.backend.security.dto.RegisterRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clear() {
        memberRepository.deleteAll();
    }

    @Test
    void registerMember() {
        RegisterRequestDto registerDto = RegisterRequestDto.builder()
                .email("test@example.com")
                .password("PASSWORD")
                .username("testName")
                .nickname("testNickname")
                .build();

        authService.register(registerDto);

        Member savedMember = memberRepository.findByEmail(registerDto.getEmail()).orElse(null);
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getNickname()).isEqualTo(registerDto.getNickname());
    }
}
