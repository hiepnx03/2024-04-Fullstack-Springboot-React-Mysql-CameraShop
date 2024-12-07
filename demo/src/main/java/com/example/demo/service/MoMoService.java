package com.example.demo.service;

import com.example.demo.config.MoMoConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class MoMoService {

    private static final String PAYMENT_URL = MoMoConfig.PaymentUrl;

    public String createPaymentRequest(double amount, String orderId, String orderInfo) {
        RestTemplate restTemplate = new RestTemplate();

        // Thông tin yêu cầu thanh toán
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("partnerCode", MoMoConfig.PartnerCode);
        requestParams.put("accessKey", MoMoConfig.AccessKey);
        requestParams.put("requestId", orderId);
        requestParams.put("amount", String.valueOf(amount));
        requestParams.put("orderInfo", orderInfo);
        requestParams.put("returnUrl", MoMoConfig.ReturnUrl);
        requestParams.put("ipnUrl", MoMoConfig.IpnUrl);
        requestParams.put("notifyUrl", MoMoConfig.IpnUrl);

        // Xử lý mã hóa và tạo chữ ký (Signature) theo yêu cầu MoMo API
        String signature = generateSignature(requestParams);
        requestParams.put("signature", signature);

        // Tạo và gửi yêu cầu POST tới MoMo API
        String url = PAYMENT_URL;
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestParams);

        // Gửi yêu cầu và nhận phản hồi
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Xử lý kết quả từ MoMo API
        return response.getBody(); // Dữ liệu trả về từ MoMo (dạng JSON hoặc URL thanh toán)
    }

    // Hàm tạo chữ ký (signature)
    private String generateSignature(Map<String, String> params) {
        // Các bước tạo chữ ký tùy thuộc vào yêu cầu của MoMo (thông thường sẽ mã hóa các thông số theo thứ tự và sử dụng key bí mật để tạo chữ ký)
        StringBuilder signatureBuilder = new StringBuilder();

        signatureBuilder.append("partnerCode=").append(MoMoConfig.PartnerCode);
        signatureBuilder.append("&accessKey=").append(MoMoConfig.AccessKey);
        signatureBuilder.append("&requestId=").append(params.get("requestId"));
        signatureBuilder.append("&amount=").append(params.get("amount"));
        signatureBuilder.append("&orderInfo=").append(params.get("orderInfo"));
        signatureBuilder.append("&returnUrl=").append(MoMoConfig.ReturnUrl);
        signatureBuilder.append("&ipnUrl=").append(MoMoConfig.IpnUrl);

        // Dùng SecretKey để mã hóa tạo chữ ký
        signatureBuilder.append("&secretKey=").append(MoMoConfig.SecretKey);

        // Trả về chữ ký đã được mã hóa (có thể dùng SHA256, HMAC hoặc phương thức khác tuỳ thuộc vào yêu cầu của MoMo)
        return sha256(signatureBuilder.toString()); // Ví dụ, hàm sha256 có thể được sử dụng để tạo chữ ký
    }

    // Hàm mã hóa SHA256
    private String sha256(String base) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
