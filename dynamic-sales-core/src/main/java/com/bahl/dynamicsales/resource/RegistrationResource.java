package com.bahl.dynamicsales.resource;

import com.bahl.dynamicsales.dto.signup.RegisterRequestDto;
import com.bahl.dynamicsales.service.RegisterService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Path("/api/auth/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    private static final Logger log = LoggerFactory.getLogger(RegistrationResource.class);

    @Inject
    RegisterService registerService;

    @POST
    public Response register(RegisterRequestDto registerRequestDto) {
        log.info("Received signup request for user: {}", registerRequestDto.getUsername());
        // Add any validation logic here in the core if needed

        try {
            return registerService.register(registerRequestDto);
        } catch (Exception ex) {
            log.error("Error during signup for user: {}", registerRequestDto.getUsername(), ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("status", false, "message", "Failed to register user", "error", "InternalServerError"))
                    .build();
        }
    }
}
