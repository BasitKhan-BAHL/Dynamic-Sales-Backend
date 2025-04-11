package com.bahl.dynamicsales.dto.login;

public class LoginRequestDto {
    private String branchCode;
    private String username;
    private String passwordHash;

    public LoginRequestDto() {
    }

    public LoginRequestDto(String branchCode, String username, String passwordHash) {
        this.branchCode = branchCode;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
