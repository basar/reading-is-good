package com.basarc.readingisgood.service.impl;

import com.basarc.readingisgood.api.ApiResponseMessage;
import com.basarc.readingisgood.util.LocalizationResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
@Slf4j
public class ReadingUserDetailService implements UserDetailsService {

    @Value("${user.username}")
    private String username;

    @Value("${user.passo}")
    private String passo;

    private final LocalizationResolver localizationResolver;

    public ReadingUserDetailService(LocalizationResolver localizationResolver) {
        this.localizationResolver = localizationResolver;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!this.username.equals(username)) {
            log.debug("Username not found: {}", username);
            throw new UsernameNotFoundException(localizationResolver.resolve(ApiResponseMessage.USER_NOT_FOUND));
        }

        return new User(username, passo, Collections.emptyList());
    }
}
