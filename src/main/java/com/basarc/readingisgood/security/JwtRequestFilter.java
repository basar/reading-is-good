package com.basarc.readingisgood.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String AUTHORIZATION_PREFIX = "Bearer";

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(@Qualifier("readingUserDetailService") UserDetailsService userDetailsService,
                            JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationTokenHeader != null && authorizationTokenHeader.startsWith(AUTHORIZATION_PREFIX)) {

            final String jwtToken = authorizationTokenHeader.substring(AUTHORIZATION_PREFIX.length()).trim();
            final String username = jwtUtil.getUsernameFromToken(jwtToken);


            if (username != null && !jwtUtil.isTokenExpired(jwtToken) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request, response);
    }
}
