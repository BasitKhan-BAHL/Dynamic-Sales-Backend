package com.bahl.dynamicsales.client;

import com.bahl.dynamicsales.dto.LoginResponseDto;
import com.bahl.dynamicsales.dto.LoginRequestDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "login-dal-api")
@Path("/dal/login")
public interface LoginDALClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    LoginResponseDto authenticate(LoginRequestDto loginRequestDto);
}
