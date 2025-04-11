package com.bahl.dynamicsales.repository;

import com.bahl.dynamicsales.model.UserEntity;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @Inject
    AgroalDataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    public UserEntity findByUsernameAndBranchId(String username, Integer branchId) {
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
            throw new RuntimeException("DB error while finding user by username and branch ID", e);
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
            LOGGER.error("Error updating last login for user ID: {}", userId, e);
            throw new RuntimeException("Error updating last login for user ID: " + userId, e);
        }
    }

    public void createUser(UserEntity userEntity) {
        String sql = "INSERT INTO Users (username, role_id, branch_id, password_hash, created_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userEntity.getUsername());
            stmt.setInt(2, userEntity.getRoleId());
            stmt.setInt(3, userEntity.getBranchId());
            stmt.setString(4, userEntity.getPasswordHash());
            stmt.setObject(5, userEntity.getCreatedDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating user", e);
            throw new RuntimeException("Error creating user", e);
        }
    }

    public UserEntity findByUsername(String username) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToUserEntity(rs);
            }
        } catch (SQLException e) {
            LOGGER.error("DB error while finding user by username: {}", username, e);
            throw new RuntimeException("DB error while finding user by username", e);
        }
        return null;
    }

    private Optional<Integer> findBranchIdByCode(String branchCode) {
        String sql = "SELECT Branch_ID FROM Branches WHERE Branch_Code = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, branchCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(rs.getInt("Branch_ID"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("DB error while finding Branch ID by code: {}", branchCode, e);
            // Consider if you want to throw a custom exception or just return empty
            // Returning empty might mask DB errors from login attempts, which can be good practice
            // throw new RuntimeException("DB error while finding Branch ID by code", e); // Or rethrow
            return Optional.empty();
        }
    }

    private UserEntity mapRowToUserEntity(ResultSet resultSet) throws SQLException {
        return new UserEntity(
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
