package com.bahl.dynamicsales.service;

import com.bahl.dynamicsales.client.LoginDALClient;
import com.bahl.dynamicsales.dto.LoginRequestDto;
import com.bahl.dynamicsales.dto.LoginResponseDto;
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
    LoginDALClient dalClient;

    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        log.info("Calling DAL for user: {}", loginRequestDto.getUsername());
        return dalClient.authenticate(loginRequestDto);
    }
}