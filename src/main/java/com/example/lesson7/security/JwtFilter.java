package com.example.lesson7.security;

import com.example.lesson7.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthService authService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        token=token.substring(7);
        if (jwtProvider.validateToken(token)) {
            if (jwtProvider.expireToken(token)) {
                String userName = jwtProvider.getUserNameFromToken(token);

                UserDetails userDetails = authService.loadUserByUsername(userName);

                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails,
                        userDetails.getPassword(), userDetails.getAuthorities());

                System.out.println(user);
                SecurityContextHolder.getContext().setAuthentication(user);

                System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        }

        doFilter(request, response, filterChain);
    }
}
