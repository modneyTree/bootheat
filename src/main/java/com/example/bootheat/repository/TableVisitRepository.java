package com.example.bootheat.repository;

import com.example.bootheat.domain.TableVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableVisitRepository extends JpaRepository<TableVisit, Long> {

    // 해당 테이블의 "가장 최근 OPEN visit" (있으면 그걸 사용)
    Optional<TableVisit> findFirstByTable_TableIdAndStatusOrderByStartedAtDesc(Long tableId, String status);

    // 다음 visit_no 계산용: 마지막 방문 번호
    Optional<TableVisit> findTopByTable_TableIdOrderByVisitNoDesc(Long tableId);
}
