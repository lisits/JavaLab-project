package ru.itis.jl.cookweb.security.utils.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import ru.itis.jl.cookweb.security.utils.AuthorizationHeaderUtil;

@Component
public class RequestUtilImpl  implements AuthorizationHeaderUtil {

    private static final String AUTHORIZATION_HEADER_NAME = "AUTHORIZATION";
    private static final String BEARER = "Bearer ";

    @Override
    public boolean hasAuthorizationToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return header != null && header.startsWith(BEARER);
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return authorizationHeader.substring(BEARER.length());
    }


}
