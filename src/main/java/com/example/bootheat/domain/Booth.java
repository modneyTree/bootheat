package com.example.bootheat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name="booth")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Booth {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="booth_id")
    private Long boothId;

    private String name;
    private String location;
    // ★ 추가
    @Column(name = "booth_account", length = 200)
    private String boothAccount;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}