package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.api.ApiResponseCode;
import com.basarc.readingisgood.dto.AuthenticationRequestDto;
import com.basarc.readingisgood.dto.AuthenticationResponseDto;
import com.basarc.readingisgood.exception.ReadingException;
import com.basarc.readingisgood.security.JwtUtil;
import com.basarc.readingisgood.service.interfaces.UserAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserAuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public AuthenticationResponseDto doAuthentication(AuthenticationRequestDto authRequest) {

        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPasso()));
        } catch (AuthenticationException ex) {
            log.debug("{} username not authenticated", authRequest.getUsername());
            throw new ReadingException(ApiResponseCode.USER_NOT_AUTHENTICATED);
        }

        User user = (User) authentication.getPrincipal();

        return AuthenticationResponseDto.builder()
                .jwtToken(jwtUtil.generateJwtToken(user.getUsername())).build();
    }
}
