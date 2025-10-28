package com.winwintravel.authapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
public class ProcessLogDto {
    UUID id;
    UUID userId;
    String inputText;
    String outputText;
    LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ProcessLogDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", inputText='" + inputText + '\'' +
                ", outputText='" + outputText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
