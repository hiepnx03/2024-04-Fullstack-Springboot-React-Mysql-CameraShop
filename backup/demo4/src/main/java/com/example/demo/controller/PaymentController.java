package com.example.demo.controller;

import com.example.demo.config.VNPayConfig;
import com.example.demo.dto.request.PaymentRequest;
import com.example.demo.dto.response.MomoCreatePaymentResponse;
import com.example.demo.dto.response.VnPayCreatePaymentResponse;
import com.example.demo.dto.response.ZaloPayCreatePaymentResponse;
import com.example.demo.service.MomoService;
import com.example.demo.service.VNPayService;
import com.example.demo.service.ZaloPayService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class PaymentController {

    private final VNPayService vnPayService;
    private final ZaloPayService zaloPayService;
    private final MomoService momoService;

    // Phương thức tạo URL thanh toán VNPay
    @PostMapping("/create-vnpay-payment-url")
    public ResponseEntity<VnPayCreatePaymentResponse> generateVNPayPaymentUrl(@RequestBody PaymentRequest paymentRequest) {
        try {
            // Gọi service để tạo URL thanh toán VNPay
            String paymentUrl = vnPayService.createPaymentRequest(paymentRequest);

            // Trả về phản hồi chứa URL thanh toán
            VnPayCreatePaymentResponse response = new VnPayCreatePaymentResponse();
            response.setPaymentUrl(paymentUrl);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            // Xử lý lỗi nếu có
            return ResponseEntity.status(500).body(new VnPayCreatePaymentResponse("Error generating payment URL", null));
        }
    }

    @PostMapping("/checkout/vnpay-return")
    public String vnpayReturn(@RequestParam Map<String, String> params) {
        // Handle the response from VNPay
        return "vnpay-return";
    }

    @PostMapping("/checkout/vnpay-notify")
    public String vnpayNotify(@RequestParam Map<String, String> params) {
        return "vnpay-notify";
    }

    @PostMapping("/vnpay-notify")
    public ResponseEntity<String> handleVNPayNotify(@RequestBody String notificationData) {
        // Process VNPay callback notification here
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/vnpay-return")
    public String handleVNPayReturn(@RequestParam Map<String, String> params) {
        // Handle the return data here and display the result to the user
        return "Payment Result: " + params;
    }


    // Phương thức tạo URL thanh toán ZaloPay
    @PostMapping("/create-zalopay-payment-url")
    public String generateZaloPayPaymentUrl(@RequestParam String orderId,
                                            @RequestParam Long amount,
                                            @RequestParam String orderInfo) {
        ZaloPayCreatePaymentResponse response = zaloPayService.createPayment(orderId, amount, orderInfo);
        return "Redirecting to ZaloPay: " + response.getOrderUrl();
    }

    @PostMapping("/create-momo-payment-url")
    public String createMomoPaymentUrl(@RequestParam String orderId,
                                       @RequestParam Long amount,
                                       @RequestParam String orderInfo) {
        MomoCreatePaymentResponse response = momoService.generateMomoPayPaymentUrl(orderId, amount, orderInfo);
        return "Redirecting to MoMo: " + response.getPayUrl();
    }
}
