package com.example.bootheat.repository;

import com.example.bootheat.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findByBooth_BoothIdAndMenuItemId(Long boothId, Long menuItemId);

    List<MenuItem> findByBooth_BoothIdAndAvailableTrue(Long boothId);

    // 매니저용: 부스의 모든 메뉴 나열 (정렬은 이름 오름차순 정도)
    List<MenuItem> findByBooth_BoothIdOrderByNameAsc(Long boothId);

    boolean existsByBooth_BoothIdAndName(Long boothId, String name);
}
