package com.bahl.dynamicsales.service;

import com.bahl.dynamicsales.client.AuthDALClient;
import com.bahl.dynamicsales.dto.signup.RegisterRequestDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RegisterService {

    private static final Logger log = LoggerFactory.getLogger(RegisterService.class);

    @Inject
    @RestClient
    AuthDALClient dalClient;

    public Response register(RegisterRequestDto registerRequestDto) {
        log.info("Calling DAL for user signup: {}", registerRequestDto.getUsername());
        return dalClient.register(registerRequestDto);
    }
}