package com.bahl.dynamicsales.client;

import com.bahl.dynamicsales.dto.login.LoginResponseDto;
import com.bahl.dynamicsales.dto.login.LoginRequestDto;
import com.bahl.dynamicsales.dto.signup.RegisterRequestDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "auth-dal-api")
@Path("/dal")
public interface AuthDALClient {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response register(RegisterRequestDto registerRequestDto);
}
