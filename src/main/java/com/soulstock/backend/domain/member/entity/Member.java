package com.soulstock.backend.domain.member.entity;

import com.soulstock.backend.security.dto.RegisterRequestDto;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime joinDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime roleUpDate;

    @PrePersist
    private void prePersist() {
        if (role == null) role = Role.BRONZE;
    }
}