package com.nagarro.statement.security.config;


import com.nagarro.statement.security.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String username = null;
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader ==null || !tokenHeader.startsWith("Bearer ")){
            log.error("token header not valid or not found");
        }else{
            token = tokenHeader.substring(7);
            try {
                username = tokenService.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.error("cant generate JWT token !!");
            } catch (ExpiredJwtException e) {
                log.error("Expired token !!");
            }
        }

        if (null != username ) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (Boolean.TRUE.equals(tokenService.validateJwtToken(token, userDetails))) {
                UsernamePasswordAuthenticationToken
                        authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}