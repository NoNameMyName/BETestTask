package com.winwintravel.authapi.service.imp;

import com.winwintravel.authapi.dto.mapping.ProcessLogMapping;
import com.winwintravel.authapi.entity.ProcessingLog;
import com.winwintravel.authapi.repository.ProcessingLogRepository;
import com.winwintravel.authapi.service.ProcessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessLogServiceImp implements ProcessLogService {

    private final ProcessLogMapping processLogMapping;
    private final ProcessingLogRepository processingLogRepository;
    @Override
    public void save(ProcessingLog processingLog) {
        processingLogRepository.save(processingLog);
    }
}
