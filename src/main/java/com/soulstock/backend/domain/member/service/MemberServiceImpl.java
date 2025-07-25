package com.soulstock.backend.domain.member.service;

import com.soulstock.backend.domain.member.MemberRepository;
import com.soulstock.backend.domain.member.entity.Member;
import com.soulstock.backend.security.dto.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public void validateBeforeRegister(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (memberRepository.existsByNickname(member.getNickname())) {
            throw new RuntimeException("Nickname already exists");
        }
    }

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    public Member toEntity(RegisterRequestDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getUsername())
                .nickname(dto.getNickname())
                .build();
    }
}
