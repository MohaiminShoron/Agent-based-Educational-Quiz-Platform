package com.seng696.Seng696.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");
            final String msisdn = httpServletRequest.getHeader("msisdn");
            String userName = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                userName = jwtService.extractUserName(jwt);
            }
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (jwtService.validateToken(jwt, msisdn)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(msisdn, null, null);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    } else{
                        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "This token has expired");
                    }

            }
        } catch (
                Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}