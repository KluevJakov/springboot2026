package ru.jafix.springproject.config.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.jafix.springproject.service.AuthService;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String TOKEN_TYPE = "Bearer ";

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String fullJwt = request.getHeader("Authorization");

        if (fullJwt == null || !fullJwt.startsWith(TOKEN_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = fullJwt.substring(TOKEN_TYPE.length());

        if (authService.authenticateByJwt(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(401);
    }
}
