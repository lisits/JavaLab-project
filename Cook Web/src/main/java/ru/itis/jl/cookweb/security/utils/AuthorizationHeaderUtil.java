package ru.itis.jl.cookweb.security.utils;

//import javax.servlet.http.HttpServletRequest;

public interface AuthorizationHeaderUtil {

    boolean hasAuthorizationToken(jakarta.servlet.http.HttpServletRequest request);

    String getToken(jakarta.servlet.http.HttpServletRequest request);
}
