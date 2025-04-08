package com.bahl.dynamicsales.repository;

import com.bahl.dynamicsales.model.LoginEntity;
import io.agroal.api.AgroalDataSource;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.*;
import java.util.Optional;

@ApplicationScoped
public class LoginRepository {

    @Inject
    AgroalDataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRepository.class);

//    public Optional<LoginEntity> findByUsername(String username) {
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
//
//            stmt.setString(1, username);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                LoginEntity login = new LoginEntity();
//                login.setUserId(Long.valueOf(rs.getString("user_id")));
//                login.setUsername(rs.getString("username"));
//                login.setRoleId(Integer.valueOf(rs.getString("role_id")));
//                login.setBranchId(Integer.valueOf(rs.getString("branch_id")));
////                login.setPasswordHash(rs.getString("password_hash"));
//
//                // Retrieve as Timestamp and convert to LocalDateTime
//                Timestamp createdTimestamp = rs.getTimestamp("created_date");
//                login.setCreatedDate(createdTimestamp != null ? createdTimestamp.toLocalDateTime() : null);
//
//                Timestamp lastLoginTimestamp = rs.getTimestamp("last_login");
//                login.setLastLogin(lastLoginTimestamp != null ? lastLoginTimestamp.toLocalDateTime() : null);
//
//                System.out.println(login.toString());
//                return Optional.of(login);
//            }
//        } catch (SQLException e) {
//            LOGGER.error("DB error while finding login by username", e);
//        }
//        return Optional.empty();
//    }

    public Optional<LoginEntity> findByUsernameAndPasswordHash(String username, String passwordHash) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password_hash = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, passwordHash); // Assuming the password sent is already hashed
            ResultSet rs = stmt.executeQuery();
            if(passwordHash.equals(rs.getString("password_hash"))){
                if (rs.next()) {
                    LoginEntity login = new LoginEntity();
                    login.setUserId(Long.valueOf(rs.getString("user_id")));
                    login.setUsername(rs.getString("username"));
                    login.setRoleId(Integer.valueOf(rs.getString("role_id")));
                    login.setBranchId(Integer.valueOf(rs.getString("branch_id")));
//                    login.setPasswordHash(rs.getString("password_hash"));

                    // Retrieve as Timestamp and convert to LocalDateTime
//                    Timestamp createdTimestamp = rs.getTimestamp("created_date");
//                    login.setCreatedDate(createdTimestamp != null ? createdTimestamp.toLocalDateTime() : null);

                    Timestamp lastLoginTimestamp = rs.getTimestamp("last_login");
                    login.setLastLogin(lastLoginTimestamp != null ? lastLoginTimestamp.toLocalDateTime() : null);

                    return Optional.of(login);
                }
            }

        } catch (SQLException e) {
            LOGGER.error("DB error while finding login by username and password hash", e);
        }
        return Optional.empty();
    }


}
