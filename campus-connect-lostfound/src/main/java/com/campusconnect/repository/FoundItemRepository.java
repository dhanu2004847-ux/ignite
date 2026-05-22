package com.campusconnect.repository;

import com.campusconnect.model.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundItemRepository extends JpaRepository<FoundItem, Long> {
}