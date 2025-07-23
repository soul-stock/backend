package com.soulstock.backend.domain.member.entity;

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

    @PrePersist
    public void prePersist() {
        if (level == null) level = Level.BRONZE;
    }

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
    private Level level;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime levelUpDate;
}