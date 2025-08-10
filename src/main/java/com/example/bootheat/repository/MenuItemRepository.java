package com.example.bootheat.repository;

import com.example.bootheat.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByBooth_BoothIdAndAvailableTrue(Long boothId);
}