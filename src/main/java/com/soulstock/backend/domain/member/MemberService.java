package com.soulstock.backend.domain.member;

import com.soulstock.backend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
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

    public Member findByUsername(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }
}
