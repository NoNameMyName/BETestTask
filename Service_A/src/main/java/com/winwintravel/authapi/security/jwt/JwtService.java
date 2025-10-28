package com.winwintravel.authapi.security.jwt;

import com.winwintravel.authapi.dto.JwtAuthenticationDto;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
@RequiredArgsConstructor
public class JwtService {

    private static final Logger LOGGER = LogManager.getLogger(JwtService.class);

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

//    private final Dotenv dotenv;

//    private final Environment environment;
//    public JwtService(Environment environment) {
//        this.environment = environment;
//        this.token = environment.getProperty("JWT.SECRET");
//    }
//    @Value("${jwt.secret}")
//    @Value("${JWT.SECRET}")
//    @Value("6a46d7f4ef1bd7e0d2aac867bded3f529566fcb1cfd1d0cc8eaf52f6188a7f1c")
//    private final String token = dotenv.get("JWT_SECRET");

    public JwtAuthenticationDto generateAuthToken(String email) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateJwtToken(email));
        jwtDto.setRefreshToken(generateRefreshToken(email));
        return jwtDto;
    }

    public JwtAuthenticationDto refreshBaseToken(String email, String refreshToken) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateJwtToken(email));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }
        catch (ExpiredJwtException expEx) {
            LOGGER.error("Token expired", expEx);
        }
        catch (UnsupportedJwtException unEx) {
            LOGGER.error("Token not supported", unEx);
        }
        catch (MalformedJwtException mexEx) {
            LOGGER.error("Token malformed", mexEx);
        }
        catch (SecurityException secEx) {
            LOGGER.error("Token security exception", secEx);
        }
        catch (Exception expEx) {
            LOGGER.error("Token exception", expEx);
        }
        return false;
    }

    private String generateJwtToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }

    private String generateRefreshToken(String email) {
        Date date = Date.from(LocalDateTime.now().plusHours(3).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
