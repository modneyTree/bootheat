package com.example.bootheat.repository;

import com.example.bootheat.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findTop10ByTable_TableIdOrderByCreatedAtDesc(Long tableId);

}