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
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}