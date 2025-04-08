package com.bahl.dynamicsales.resource;

import com.bahl.dynamicsales.model.LoginEntity;
import com.bahl.dynamicsales.model.LoginRequestDto;
import com.bahl.dynamicsales.repository.LoginRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dal/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    LoginRepository loginRepo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> authenticate(LoginRequestDto loginEntity) { // Expect LoginEntity
        System.out.println("-------> Received username in DAL: " + loginEntity.getUsername());
        return Uni.createFrom().item(() ->
                // In a real scenario, compare loginEntity.getPasswordHash()
                // with storedEntity.getPasswordHash() after proper hashing.
                // For now, just returning the stored entity if username matches.
                loginRepo.findByUsernameAndPasswordHash(loginEntity.getUsername(), loginEntity.getPassword())
                        .map(Response::ok)
                        .orElse(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials"))
                        .build()
        );
    }
}