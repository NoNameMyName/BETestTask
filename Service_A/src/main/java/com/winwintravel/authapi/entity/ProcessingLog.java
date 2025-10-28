package com.winwintravel.authapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processing_log")
public class ProcessingLog {

    @Id
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @Setter
    @Getter
    private UUID id;

    @Column(name = "user_id")
    @Setter
    @Getter
    private UUID userId;

    @Column(name = "input_text")
    @Setter
    @Getter
    private String inputText;

    @Column(name = "output_text")
    @Setter
    @Getter
    private String outputText;

    @Column(name = "created_at")
    @Setter
    @Getter
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ProcessingLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", inputText='" + inputText + '\'' +
                ", outputText='" + outputText + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}