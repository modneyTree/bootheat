package com.example.bootheat.repository;

import com.example.bootheat.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {}