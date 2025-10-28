package com.winwintravel.authapi.dto.mapping;

import com.winwintravel.authapi.dto.ProcessLogDto;
import com.winwintravel.authapi.entity.ProcessingLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessLogMapping {
    ProcessLogDto toDto(ProcessingLog ProcessingLog);
    ProcessingLog toEntity(ProcessLogDto ProcessLogDto);
}
