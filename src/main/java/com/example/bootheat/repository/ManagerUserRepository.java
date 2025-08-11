package com.example.bootheat.repository;

import com.example.bootheat.domain.ManagerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// repository/ManagerUserRepository.java
public interface ManagerUserRepository extends JpaRepository<ManagerUser, Long> {
    // 가장 먼저 생성된 운영자 1명 기준(데모)
    Optional<ManagerUser> findFirstByBooth_BoothIdOrderByCreatedAtAsc(Long boothId);
}
