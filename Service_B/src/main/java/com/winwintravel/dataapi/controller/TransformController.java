package com.winwintravel.dataapi.controller;

import com.winwintravel.dataapi.dto.TextRequestDto;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransformController {

    @Value("${INTERNAL_TOKEN}")
    private String internalToken;

    @PostMapping("/transform")
    public ResponseEntity<TextRequestDto> transform(@RequestHeader("X-Internal-Token") String token, @RequestBody TextRequestDto body) {
        System.out.println("Received token: " + token);
        System.out.println("Received body text: " + (body != null ? body.getText() : "NULL"));
        if (!internalToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        TextRequestDto textRequestDto = new TextRequestDto();
        textRequestDto.setText(new StringBuilder(body.getText()).reverse().toString());
        return ResponseEntity.status(HttpStatus.OK).body(textRequestDto);
    }
}
