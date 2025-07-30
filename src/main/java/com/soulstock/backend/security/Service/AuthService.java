package com.soulstock.backend.security.Service;

import com.soulstock.backend.domain.member.entity.Member;
import com.soulstock.backend.domain.member.service.MemberService;
import com.soulstock.backend.security.dto.LoginRequestDto;
import com.soulstock.backend.security.dto.RegisterRequestDto;
import com.soulstock.backend.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequestDto registerDto) {
        try {
            Member member = memberService.toEntity(registerDto);
            memberService.validateBeforeRegister(member);
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberService.registerMember(member);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("회원가입 처리 중 오류가 발생", e);
        }
    }

    public String login(LoginRequestDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            return jwtTokenUtil.generateJwtToken(email);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("이메일 또는 비밀번호 오류");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("로그인 처리 중 오류 발생");
        }
    }

    public String getEmailFromJwtToken(String jwtToken) {
        return jwtTokenUtil.getEmailFromJwtToken(jwtToken);
    }
}