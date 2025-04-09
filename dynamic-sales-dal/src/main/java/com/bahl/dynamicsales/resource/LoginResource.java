package com.bahl.dynamicsales.resource;

import com.bahl.dynamicsales.model.LoginEntity;
import com.bahl.dynamicsales.repository.LoginRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

@Path("/dal/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    LoginRepository loginRepo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> authenticate(LoginEntity loginEntity) {
        System.out.println("-------> Received username in DAL: " + loginEntity.getUsername());
        LoginEntity storedEntity = loginRepo.findByUsernameAndBranchId(loginEntity.getUsername(), loginEntity.getBranchId());
        if (storedEntity != null) {
            System.out.println("-------> User found in DAL: " + storedEntity.getUsername());

            System.out.println("-------> Password in Login Entity: " + loginEntity.getPasswordHash());
            System.out.println("-------> Password in Stored Entity: " + storedEntity.getPasswordHash());

            boolean passwordMatch = BCrypt.checkpw(loginEntity.getPasswordHash(), storedEntity.getPasswordHash());
            System.out.println("-------> Password match result: " + passwordMatch);
            if (passwordMatch) {
                loginRepo.updateLastLogin(storedEntity.getUserId());
                return Uni.createFrom().item(Response.ok(storedEntity).build());
            } else {
                return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build());
            }
        } else {
            System.out.println("-------> User not found in DAL: " + loginEntity.getUsername());
            return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build());
        }
    }
}