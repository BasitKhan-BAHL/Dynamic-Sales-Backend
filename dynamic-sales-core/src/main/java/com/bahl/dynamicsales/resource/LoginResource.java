package com.bahl.dynamicsales.resource;

import com.bahl.dynamicsales.dto.LoginResponseDto;
import com.bahl.dynamicsales.dto.LoginRequestDto;
import com.bahl.dynamicsales.service.LoginService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private static final Logger log = LoggerFactory.getLogger(LoginResource.class);

    @Inject
    LoginService loginService;

    @POST
    public Response login(LoginRequestDto loginRequestDto) {
        if (loginRequestDto.getUsername() == null || loginRequestDto.getUsername().trim().isEmpty() || loginRequestDto.getPassword() == null || loginRequestDto.getPassword().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Username and password are required").build();
        }

        try {
            LoginResponseDto loginResponse = loginService.authenticate(loginRequestDto);
            if (loginResponse == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid credentials").build();
            }
            return Response.ok(loginResponse).build();
        } catch (Exception ex) {
            log.error("Error during login for user: {}", loginRequestDto.getUsername(), ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred").build();
        }
    }
}