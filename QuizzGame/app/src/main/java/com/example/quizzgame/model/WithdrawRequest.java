package com.example.quizzgame.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawRequest {
    private String userId;
    private String emailAddress;
    private String requestdBy;

    @ServerTimestamp
    private Date createAt;

    public WithdrawRequest() {
    }

    public WithdrawRequest(String userId, String emailAddress, String requestdBy) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.requestdBy = requestdBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRequestdBy() {
        return requestdBy;
    }

    public void setRequestdBy(String requestdBy) {
        this.requestdBy = requestdBy;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
