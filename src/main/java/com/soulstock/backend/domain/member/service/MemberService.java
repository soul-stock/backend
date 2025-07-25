package com.soulstock.backend.domain.member.service;

import com.soulstock.backend.domain.member.entity.Member;
import com.soulstock.backend.security.dto.RegisterRequestDto;

public interface MemberService {
    void validateBeforeRegister(Member member);
    void registerMember(Member member);
    Member toEntity(RegisterRequestDto dto);
}
