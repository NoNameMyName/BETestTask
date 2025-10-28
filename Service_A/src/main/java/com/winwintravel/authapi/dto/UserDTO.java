package com.winwintravel.authapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class UserDTO {
    String email;
    String passwordHash;
    UUID id;


    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", uuid=" + id +
                '}';
    }
}
