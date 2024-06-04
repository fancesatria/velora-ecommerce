package com.uas.nb_official.Response;

public class PaymentResponse {
    private boolean success;
    private String message;
    private String snap_token;

    public PaymentResponse(boolean success, String message, String snapToken) {
        this.success = success;
        this.message = message;
        this.snap_token = snapToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSnap_token() {
        return snap_token;
    }

    public void setSnap_token(String snap_token) {
        this.snap_token = snap_token;
    }
}
