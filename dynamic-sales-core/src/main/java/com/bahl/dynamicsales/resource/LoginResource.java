package com.bahl.dynamicsales.resource;

import com.bahl.dynamicsales.dto.LoginResponseDto;
import com.bahl.dynamicsales.dto.LoginRequestDto;
import com.bahl.dynamicsales.service.LoginService;
import com.bahl.dynamicsales.utils.ErrorResponseBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private static final Logger log = LoggerFactory.getLogger(LoginResource.class);

    @Inject
    LoginService loginService;

    @POST
    public Response login(LoginRequestDto loginRequestDto) {
        if (loginRequestDto == null || loginRequestDto.getBranchId() == null || loginRequestDto.getUsername() == null || loginRequestDto.getUsername().trim().isEmpty() || loginRequestDto.getPasswordHash() == null || loginRequestDto.getPasswordHash().trim().isEmpty()) {
            return ErrorResponseBuilder.buildBadRequestResponse("Username, Branch ID, and password are required");
        }

        try {
            LoginResponseDto loginResponse = loginService.authenticate(loginRequestDto);
            return Response.ok(Map.of("data", loginResponse, "message", "You have been logged in successfully.", "success", true)).build(); // For successful response
        } catch (ClientWebApplicationException ex) {
            if (ex.getResponse().getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
                return ErrorResponseBuilder.buildUnauthorizedResponse("Invalid credentials");
            } else {
                log.error("Error during login for user: {}", loginRequestDto.getUsername(), ex);
                return ErrorResponseBuilder.buildErrorResponse(false, "An unexpected error occurred", Response.Status.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            log.error("Error during login for user: {}", loginRequestDto.getUsername(), ex);
            return ErrorResponseBuilder.buildErrorResponse(false, "An unexpected error occurred", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}