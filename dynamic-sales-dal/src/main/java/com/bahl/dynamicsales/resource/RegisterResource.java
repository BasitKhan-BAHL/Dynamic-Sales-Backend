package com.bahl.dynamicsales.resource;

import com.bahl.dynamicsales.model.UserEntity;
import com.bahl.dynamicsales.repository.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDateTime;

@Path("/dal/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegisterResource {

    @Inject
    UserRepository userRepository;

    @POST
    public Uni<Response> register(UserEntity newUserEntity) {
        System.out.println("-------> Received signup request for username: " + newUserEntity.getUsername());

        // Basic validation
        if (newUserEntity.getUsername() == null || newUserEntity.getUsername().trim().isEmpty() ||
                newUserEntity.getPasswordHash() == null || newUserEntity.getPasswordHash().trim().isEmpty() ||
                newUserEntity.getBranchId() == null || newUserEntity.getRoleId() == null) {
            return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST)
                    .entity("Username, password, branch ID, and role ID are required.").build());
        }

        // Check if the username already exists
        if (userRepository.findByUsername(newUserEntity.getUsername()) != null) {
            return Uni.createFrom().item(Response.status(Response.Status.CONFLICT)
                    .entity("Username already exists.").build());
        }

        // Hash the password
        String hashedPassword = BCrypt.hashpw(newUserEntity.getPasswordHash(), BCrypt.gensalt());
        newUserEntity.setPasswordHash(hashedPassword);
        newUserEntity.setCreatedDate(LocalDateTime.now());

        try {
            userRepository.createUser(newUserEntity);
            return Uni.createFrom().item(Response.status(Response.Status.CREATED)
                    .entity("User registered successfully.").build());
        } catch (Exception e) {
            System.err.println("-------> Error during signup: " + e.getMessage());
            return Uni.createFrom().item(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to register user.").build());
        }
    }
}

