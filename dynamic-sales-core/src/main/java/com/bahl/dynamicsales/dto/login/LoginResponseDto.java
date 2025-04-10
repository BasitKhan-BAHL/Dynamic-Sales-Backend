package com.bahl.dynamicsales.dto.login;

import java.time.LocalDateTime;

public class LoginResponseDto {
    private Integer userId;
    private String username;
    private Integer roleId;
    private Integer branchId;
    private LocalDateTime lastLogin;

    public LoginResponseDto() {
    }

    public LoginResponseDto(Integer userId, String username, Integer roleId, Integer branchId, LocalDateTime lastLogin) {
        this.userId = userId;
        this.username = username;
        this.roleId = roleId;
        this.branchId = branchId;
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

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "LoginResponseDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", roleId=" + roleId +
                ", branchId=" + branchId +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
