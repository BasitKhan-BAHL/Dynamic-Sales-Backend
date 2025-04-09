package com.bahl.dynamicsales.repository;

import com.bahl.dynamicsales.model.LoginEntity;
import io.agroal.api.AgroalDataSource;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.*;
import java.time.LocalDateTime;

@ApplicationScoped
public class LoginRepository {

    @Inject
    AgroalDataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRepository.class);

    public LoginEntity findByUsernameAndBranchId(String username, Integer branchId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND branch_id = ?")) {

            stmt.setString(1, username);
            stmt.setInt(2, branchId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToUserEntity(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("DB error while finding login by username and branch ID", e);
        }
        return null;
    }

    public void updateLastLogin(Integer userId) {
        String sql = "UPDATE Users SET LAST_LOGIN = ? WHERE USER_ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, LocalDateTime.now());
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error updating last login for user ID: {}");
        }
    }

    private LoginEntity mapRowToUserEntity(ResultSet resultSet) throws SQLException {
        return new LoginEntity(
                resultSet.getInt("USER_ID"),
                resultSet.getString("USERNAME"),
                resultSet.getInt("ROLE_ID"),
                resultSet.getInt("BRANCH_ID"),
                resultSet.getString("PASSWORD_HASH"),
                resultSet.getObject("CREATED_DATE", LocalDateTime.class),
                resultSet.getObject("LAST_LOGIN", LocalDateTime.class)
        );
    }
}
