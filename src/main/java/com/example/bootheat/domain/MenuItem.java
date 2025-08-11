package com.example.bootheat.domain;

import com.example.bootheat.support.Category;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="menu_item")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_item_id")
    private Long menuItemId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="booth_id", nullable=false)
    private Booth booth;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Category category; // ★ 추가 (기존 row엔 기본값 필요)
    private Integer price;
    @Builder.Default
    private Boolean available = true;

    private String description;
    private String modelUrl;
    private String previewImage;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
