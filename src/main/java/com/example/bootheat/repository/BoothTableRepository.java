package com.example.bootheat.repository;

import com.example.bootheat.domain.BoothTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoothTableRepository extends JpaRepository<BoothTable, Long> {
    Optional<BoothTable> findByBooth_BoothIdAndTableNumber(Long boothId, Integer tableNo);
    boolean existsByBooth_BoothIdAndTableNumber(Long boothId, Integer tableNo);
    List<BoothTable> findByBooth_BoothIdOrderByTableNumberAsc(Long boothId);
}