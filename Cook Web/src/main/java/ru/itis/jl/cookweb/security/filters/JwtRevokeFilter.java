package ru.itis.jl.cookweb.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.jl.cookweb.security.repositories.BlacklistRepository;
import ru.itis.jl.cookweb.security.utils.AuthorizationHeaderUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRevokeFilter extends OncePerRequestFilter {
    public static final String REVOKE_TOKEN_URL = "/auth/token/revoke";

    private final BlacklistRepository blacklistRepository;
    private final AuthorizationHeaderUtil authorizationHeaderUtil;

    private final AntPathRequestMatcher revokeMatcher = new AntPathRequestMatcher(REVOKE_TOKEN_URL, "POST");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (revokeMatcher.matches(request)) {
            String token = authorizationHeaderUtil.getToken(request);
            blacklistRepository.save(token);
            response.setStatus(200);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

