package com.soulstock.backend.security;

import com.soulstock.backend.domain.member.MemberRepository;
import com.soulstock.backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityAdapter {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberService getUserService() {
        return memberService;
    }

    public MemberRepository getUserRepository() {
        return memberRepository;
    }
}
