package ru.jafix.springproject.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.jafix.springproject.dto.auth.AuthDto;
import ru.jafix.springproject.dto.auth.JwtResponse;
import ru.jafix.springproject.model.User;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    @Value("${jwt.key}")
    private String secretKey;

    public JwtResponse auth(AuthDto authDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authDto.getLogin(), authDto.getPassword());

        try {
            token = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);

            User currentUser = (User) token.getPrincipal();

            String jwt = generateJwt(currentUser.getId());

            return JwtResponse.builder()
                    .token(jwt)
                    .build();
        } catch (DisabledException ex) {
            log.error("Учетная запись не активирована: {}", ex.getMessage());
            throw ex;
        } catch (LockedException ex) {
            log.error("Учетная запись заблокирована: {}", ex.getMessage());
            throw ex;
        } catch (BadCredentialsException ex) {
            log.error("Неверные учетные данные: {}", ex.getMessage());
            throw ex;
        }
    }

    public String generateJwt(UUID id) {
        Date now = new Date();

        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .subject()
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public boolean validateJwt(String jwt) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt);

            return true;
        } catch (JwtException ex) {
            log.error("Ошибка аутентификации: {}", ex.getMessage());
            return false;
        }
    }
}
