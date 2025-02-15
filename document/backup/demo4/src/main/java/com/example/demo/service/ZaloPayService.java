package com.example.demo.service;

import com.example.demo.dto.request.ZaloPayCreatePaymentRequest;
import com.example.demo.dto.response.ZaloPayCreatePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZaloPayService {

    @Value("${zalo.pay.api-url}")
    private String apiUrl;

    @Value("${zalo.pay.partner-code}")
    private String partnerCode;

    @Value("${zalo.pay.access-key}")
    private String accessKey;

    @Value("${zalo.pay.secret-key}")
    private String secretKey;

    @Value("${zalo.pay.return-url}")
    private String returnUrl;

    @Value("${zalo.pay.notify-url}")
    private String notifyUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public ZaloPayCreatePaymentResponse createPayment(String orderId, long amount, String orderInfo) {
        // Tạo đối tượng yêu cầu ZaloPay
        ZaloPayCreatePaymentRequest request = new ZaloPayCreatePaymentRequest();
        request.setOrderId(orderId);
        request.setAmount(amount);
        request.setOrderInfo(orderInfo);
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);

        // Tạo tham số để ký
        Map<String, String> params = new HashMap<>();
        params.put("partnerCode", partnerCode);
        params.put("accessKey", accessKey);
        params.put("orderId", orderId);
        params.put("amount", String.valueOf(amount));
        params.put("orderInfo", orderInfo);
        params.put("returnUrl", returnUrl);
        params.put("notifyUrl", notifyUrl);

        // Tạo chữ ký
        String signData = createSignData(params);
        String secureHash = generateSecureHash(signData);

        // Thêm chữ ký vào yêu cầu
        Map<String, String> requestData = new HashMap<>(params);
        requestData.put("secureHash", secureHash);

        // Gửi yêu cầu đến ZaloPay
        ZaloPayCreatePaymentResponse response = restTemplate.postForObject(apiUrl, requestData, ZaloPayCreatePaymentResponse.class);

        return response;
    }

    private String createSignData(Map<String, String> params) {
        StringBuilder signData = new StringBuilder();

        // Sắp xếp các tham số theo thứ tự từ A-Z
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> signData.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));

        // Cắt dấu "&" thừa ở cuối
        signData.deleteCharAt(signData.length() - 1);

        return signData.toString();
    }

    private String generateSecureHash(String signData) {
        try {
            // Tạo đối tượng MessageDigest cho SHA-256
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Cập nhật dữ liệu cần mã hóa
            messageDigest.update(signData.getBytes("UTF-8"));

            // Lấy kết quả mã băm (mảng byte)
            byte[] hashBytes = messageDigest.digest();

            // Chuyển đổi mảng byte thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Chuyển mỗi byte thành 2 ký tự hex
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString(); // Trả về chuỗi hex
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secure hash", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
