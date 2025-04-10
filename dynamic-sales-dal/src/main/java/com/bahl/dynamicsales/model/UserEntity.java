package com.bahl.dynamicsales.model;

import java.time.LocalDateTime;

public class UserEntity {
    private Integer userId;
    private String username;
    private Integer roleId;
    private Integer branchId;
    private String passwordHash;
    private LocalDateTime createdDate;
    private LocalDateTime lastLogin;

    public UserEntity() {
    }

    public UserEntity(Integer userId, String username, Integer roleId, Integer branchId, String passwordHash, LocalDateTime createdDate, LocalDateTime lastLogin) {
        this.userId = userId;
        this.username = username;
        this.roleId = roleId;
        this.branchId = branchId;
        this.passwordHash = passwordHash;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", roleId=" + roleId +
                ", branchId=" + branchId +
                ", passwordHash='" + passwordHash + '\'' +
                ", createdDate=" + createdDate +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
