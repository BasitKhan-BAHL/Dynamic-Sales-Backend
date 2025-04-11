package com.bahl.dynamicsales.resource;

import com.bahl.dynamicsales.dto.DalLoginRequest;
import com.bahl.dynamicsales.model.UserEntity;
import com.bahl.dynamicsales.repository.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;

@Path("/dal/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    UserRepository userRepo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> login(UserEntity userEntity) {
        System.out.println("-------> Received username in DAL: " + userEntity.getUsername());
        UserEntity storedEntity = userRepo.findByUsernameAndBranchId(userEntity.getUsername(), userEntity.getBranchId());
        if (storedEntity != null) {
            System.out.println("-------> User found in DAL: " + storedEntity.getUsername());

            System.out.println("-------> Password in Login Entity: " + userEntity.getPasswordHash());
            System.out.println("-------> Password in Stored Entity: " + storedEntity.getPasswordHash());

            boolean passwordMatch = BCrypt.checkpw(userEntity.getPasswordHash(), storedEntity.getPasswordHash());
            System.out.println("-------> Password match result: " + passwordMatch);
            if (passwordMatch) {
                userRepo.updateLastLogin(storedEntity.getUserId());
                return Uni.createFrom().item(Response.ok(storedEntity).build());
            } else {
                return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build());
            }
        } else {
            System.out.println("-------> User not found in DAL: " + userEntity.getUsername());
            return Uni.createFrom().item(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build());
        }
    }
}