package ru.itis.jl.cookweb.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.itis.jl.cookweb.security.authentication.RefreshTokenAuthentication;
import ru.itis.jl.cookweb.security.details.UserDetailsImpl;
import ru.itis.jl.cookweb.security.utils.AuthorizationHeaderUtil;
import ru.itis.jl.cookweb.security.utils.JwtUtil;

//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String USERNAME_PARAMETER = "email";
    public static final String AUTHENTICATION_URL = "/auth/token";

    private final ObjectMapper objectMapper;

    private final AuthorizationHeaderUtil authorizationHeaderUtil;

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthenticationConfiguration authenticationConfiguration,
                                   ObjectMapper objectMapper, AuthorizationHeaderUtil authorizationHeaderUtil) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        this.setUsernameParameter(USERNAME_PARAMETER);
        this.setFilterProcessesUrl(AUTHENTICATION_URL);
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.authorizationHeaderUtil = authorizationHeaderUtil;
    }



    @Override
    protected void successfulAuthentication(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain chain, Authentication authResult) throws IOException, jakarta.servlet.ServletException {
        response.setContentType("application/json");
        GrantedAuthority currentAuthority = authResult.getAuthorities().stream().findFirst().orElseThrow();
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        String issuer = request.getRequestURL().toString();

        Map<String,String> tokens = jwtUtil.generateTokens(email, currentAuthority.getAuthority(), issuer);

        objectMapper.writeValue(response.getOutputStream(), tokens);

    }

    @Override
    protected void unsuccessfulAuthentication(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException failed) throws IOException, jakarta.servlet.ServletException {
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws AuthenticationException {
        if (authorizationHeaderUtil.hasAuthorizationToken(request)) {
            String refreshToken = authorizationHeaderUtil.getToken(request);
            RefreshTokenAuthentication authentication = new RefreshTokenAuthentication(refreshToken);
            return  super.getAuthenticationManager().authenticate(authentication);
        }
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
