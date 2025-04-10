package com.bahl.dynamicsales.service;

import com.bahl.dynamicsales.client.AuthDALClient;
import com.bahl.dynamicsales.dto.login.LoginRequestDto;
import com.bahl.dynamicsales.dto.login.LoginResponseDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    @Inject
    @RestClient
    AuthDALClient dalClient;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        log.info("Calling DAL for user: {}", loginRequestDto.getUsername());
        return dalClient.login(loginRequestDto);
    }
}