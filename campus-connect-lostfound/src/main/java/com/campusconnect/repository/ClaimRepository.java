package com.campusconnect.repository;

import com.campusconnect.model.ClaimRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<ClaimRequest, Long> {
}