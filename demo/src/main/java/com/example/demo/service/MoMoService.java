package com.example.demo.service;

import com.example.demo.dto.response.MomoCreatePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MoMoService {

    @Value("${momo.api.url}")
    private String momoApiUrl;

    @Value("${momo.secret.key}")
    private String secretKey;

    @Value("${momo.access.key}")
    private String accessKey;

    @Value("${momo.partner.code}")
    private String partnerCode;

    @Value("${momo.return.url}")
    private String returnUrl;

    @Value("${momo.notify.url}")
    private String notifyUrl;

    @Value("${momo.request.type}")
    private String requestType;

    private final RestTemplate restTemplate;

    public MoMoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MomoCreatePaymentResponse generateMomoPayPaymentUrl(String orderId, Long amount, String orderInfo) {
        try {
            // Dữ liệu yêu cầu gửi tới MoMo API
            Map<String, String> data = new HashMap<>();
            data.put("partnerCode", partnerCode);
            data.put("accessKey", accessKey);
            data.put("requestId", orderId);
            data.put("amount", String.valueOf(amount));
            data.put("orderId", orderId);
            data.put("orderInfo", orderInfo);
            data.put("returnUrl", returnUrl);
            data.put("notifyUrl", notifyUrl);
            data.put("requestType", requestType);
            data.put("extraData", "");

            // Tính toán chữ ký (signature)
            String rawData = buildRawData(data);
            String signature = generateSignature(rawData);

            // Thêm chữ ký vào dữ liệu yêu cầu
            data.put("signature", signature);

            // Gửi yêu cầu POST tới MoMo API để tạo URL thanh toán
            Map<String, String> response = restTemplate.postForObject(momoApiUrl, data, Map.class);

            // Xử lý phản hồi từ MoMo API và trả về URL thanh toán
            if ("00".equals(response.get("errorCode"))) {
                return new MomoCreatePaymentResponse(response.get("payUrl"));
            } else {
                throw new RuntimeException("Error from MoMo: " + response.get("message"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating MoMo payment URL", e);
        }
    }

    // Xây dựng dữ liệu thô cho chữ ký
    private String buildRawData(Map<String, String> data) {
        return "partnerCode=" + data.get("partnerCode")
                + "&accessKey=" + data.get("accessKey")
                + "&requestId=" + data.get("requestId")
                + "&amount=" + data.get("amount")
                + "&orderId=" + data.get("orderId")
                + "&orderInfo=" + data.get("orderInfo")
                + "&returnUrl=" + data.get("returnUrl")
                + "&notifyUrl=" + data.get("notifyUrl")
                + "&extraData=" + data.get("extraData");
    }

    // Tính toán chữ ký HMAC SHA256
    private String generateSignature(String rawData) {
        return computeHmacSha256(rawData, secretKey);
    }

    // Hàm tính toán chữ ký HMAC SHA256
    private String computeHmacSha256(String data, String secretKey) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hashBytes = mac.doFinal(data.getBytes());
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error computing HMAC", e);
        }
    }

    // Chuyển byte[] thành chuỗi hex
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
