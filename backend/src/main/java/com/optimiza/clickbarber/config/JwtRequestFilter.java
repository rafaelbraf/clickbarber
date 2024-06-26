package com.optimiza.clickbarber.config;

import com.optimiza.clickbarber.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", Constants.FRONTEND_BARBEARIA_URL);
        response.setHeader("Access-Control-Allow-Origin", Constants.FRONTEND_CLIENTE_URL);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (request.getMethod().equalsIgnoreCase(Constants.OPTIONS_METHOD)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (!isRefererValido(request)) {
            String authorizationHeader = request.getHeader(Constants.AUTHORIZATION_NAME);

            String token = null;
            String email = null;

            if (nonNull(authorizationHeader) && authorizationHeader.startsWith(Constants.BEARER_PREFIX)) {
                token = authorizationHeader.substring(7);
                try {
                    email = jwtUtil.extraiEmail(token);
                } catch (ExpiredJwtException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.Error.TOKEN_EXPIRADO);
                    return;
                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.Error.TOKEN_INVALIDO);
                    return;
                }
            }

            if (nonNull(token) && jwtUtil.isTokenValido(token)) {
                request.setAttribute("email", email);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Constants.Error.TOKEN_INVALIDO);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isRefererValido(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return nonNull(referer) && referer.startsWith(Constants.FRONTEND_CLIENTE_URL);
    }
}
