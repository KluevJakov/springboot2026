package ru.jafix.springproject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
import ru.jafix.springproject.dto.auth.TrustedToken;
import ru.jafix.springproject.model.Role;
import ru.jafix.springproject.model.User;

import javax.crypto.SecretKey;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private static final String ROLE_CLAIM_KEY = "role";

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

            String jwt = generateJwt(currentUser.getLogin(), currentUser.getRole().getName());

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

    public String generateJwt(String login, String roleName) {
        Date now = new Date();

        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .subject(login)
                .claim(ROLE_CLAIM_KEY, roleName)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationTime))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public boolean authenticateByJwt(String jwt) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        try {
            Jws<Claims> result = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt);

            String login = result.getPayload().getSubject();
            String roleName = result.getPayload().get(ROLE_CLAIM_KEY, String.class);
            Role role = Role.builder()
                    .name(roleName)
                    .build();

            TrustedToken token = TrustedToken.builder()
                    .login(login)
                    .authorities(List.of(role))
                    .authenticated(true)
                    .build();

            SecurityContextHolder.getContext().setAuthentication(token);

            return true;
        } catch (JwtException ex) {
            log.error("Ошибка аутентификации: {}", ex.getMessage());
            return false;
        }
    }
}
