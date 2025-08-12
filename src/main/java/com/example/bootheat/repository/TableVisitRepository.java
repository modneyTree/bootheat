package com.example.bootheat.repository;

import com.example.bootheat.domain.TableVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableVisitRepository extends JpaRepository<TableVisit, Long> {

    // 현재 OPEN 상태의 최신 방문 (startedAt 내림차순)
    Optional<TableVisit> findFirstByTable_TableIdAndStatusOrderByStartedAtDesc(Long tableId, String status);

    // visit_no 기준 가장 최근 방문
    Optional<TableVisit> findTopByTable_TableIdOrderByVisitNoDesc(Long tableId);

    // startedAt 기준 가장 최근 방문 (OPEN 없을 때 fallback 용)
    Optional<TableVisit> findTopByTable_TableIdOrderByStartedAtDesc(Long tableId);

    boolean existsByTable_TableIdAndStatus(Long tableId, String status);

}