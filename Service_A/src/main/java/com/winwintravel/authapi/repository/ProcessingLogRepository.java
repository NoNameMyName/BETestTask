package com.winwintravel.authapi.repository;

import com.winwintravel.authapi.entity.ProcessingLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, UUID> {
}