package com.example.bootheat.repository;

import com.example.bootheat.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoothRepository extends JpaRepository<Booth, Long> {}