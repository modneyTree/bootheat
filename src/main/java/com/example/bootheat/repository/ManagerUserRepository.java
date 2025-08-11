// repository/ManagerUserRepository.java
package com.example.bootheat.repository;

import com.example.bootheat.domain.ManagerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerUserRepository extends JpaRepository<ManagerUser, Long> {
    Optional<ManagerUser> findFirstByBooth_BoothIdOrderByCreatedAtAsc(Long boothId);

    // ★ 추가
    Optional<ManagerUser> findByBooth_BoothId(Long boothId);
    boolean existsByBooth_BoothId(Long boothId);
    boolean existsByUsername(String username);
}
