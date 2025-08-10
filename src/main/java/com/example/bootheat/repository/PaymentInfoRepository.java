package com.example.bootheat.repository;

import com.example.bootheat.domain.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    Optional<PaymentInfo> findByOrder_OrderId(Long orderId);
}