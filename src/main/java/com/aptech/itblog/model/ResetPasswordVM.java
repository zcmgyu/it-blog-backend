package com.aptech.itblog.model;

public class ResetPasswordVM {
    private String password;
    private String confirmedPassword;
    private String resetToken;

    public ResetPasswordVM(String password, String confirmedPassword, String resetToken) {
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.resetToken = resetToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
