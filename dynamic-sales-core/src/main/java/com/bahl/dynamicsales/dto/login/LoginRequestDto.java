package com.bahl.dynamicsales.dto.login;

public class LoginRequestDto {
    private Integer branchId;
    private String username;
    private String passwordHash;

    public LoginRequestDto() {
    }

    public LoginRequestDto(Integer branchId, String username, String passwordHash) {
        this.branchId = branchId;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
