package com.soulstock.backend.security.Service;

import com.soulstock.backend.domain.member.entity.Member;
import com.soulstock.backend.security.SecurityAdapter;
import com.soulstock.backend.security.dto.LoginRequestDto;
import com.soulstock.backend.security.dto.RegisterRequestDto;
import com.soulstock.backend.security.dto.UserDetailsImpl;
import com.soulstock.backend.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final SecurityAdapter securityAdapter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public void register(RegisterRequestDto registerDto) {
        try {
            Member member = securityAdapter.getUserService().toEntity(registerDto);
            securityAdapter.getUserService().validateBeforeRegister(member);
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            securityAdapter.getUserService().registerMember(member);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("회원가입 처리 중 오류가 발생", e);
        }
    }

    public String login(LoginRequestDto loginDto) {
        try {
            Authentication authentication = authenticateUser(loginDto);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            return getEmailFromJwtToken(email);

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("이메일 또는 비밀번호 오류");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("로그인 처리 중 오류 발생");
        }
    }

    private Authentication authenticateUser(LoginRequestDto loginDto) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return authenticationManager.authenticate(authToken);
    }


    public String getEmailFromJwtToken(String jwtToken) {
        return jwtTokenUtil.getEmailFromJwtToken(jwtToken);
    }

    public UserDetails getUserDetails(String email) {
        Member member = securityAdapter.getUserRepository().findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
        return new UserDetailsImpl(member);
    }
}