package com.soulstock.backend.security;

import com.soulstock.backend.domain.member.MemberRepository;
import com.soulstock.backend.domain.member.entity.Member;
import com.soulstock.backend.security.dto.UserDto;
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
        UserDto dto = UserDto.builder()
                .email("test@example.com")
                .password("PASSWORD")
                .name("testName")
                .nickname("testNickname")
                .build();

        authService.registerUser(dto);

        Member savedMember = memberRepository.findByEmail(dto.getEmail()).orElse(null);
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getNickname()).isEqualTo(dto.getNickname());
    }
}
