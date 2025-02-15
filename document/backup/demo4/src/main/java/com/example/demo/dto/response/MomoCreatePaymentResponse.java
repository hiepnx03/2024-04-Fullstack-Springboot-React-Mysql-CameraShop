package com.example.demo.dto.response;

public class MomoCreatePaymentResponse {

    private String payUrl;

    public MomoCreatePaymentResponse(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }
}
