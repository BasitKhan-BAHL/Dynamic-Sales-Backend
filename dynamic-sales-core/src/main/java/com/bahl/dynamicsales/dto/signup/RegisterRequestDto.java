package com.bahl.dynamicsales.dto.signup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequestDto {
    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String passwordHash;

    @NotNull(message = "Role ID is required")
    private Integer roleId;

    @NotNull(message = "Branch ID is required")
    private Integer branchId;

    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String username, String passwordHash, Integer roleId, Integer branchId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
        this.branchId = branchId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
