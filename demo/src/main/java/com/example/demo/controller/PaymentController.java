package com.example.demo.controller;

import com.example.demo.config.VNPayConfig;
import com.example.demo.dto.request.PaymentRequest;
import com.example.demo.dto.response.MomoCreatePaymentResponse;
import com.example.demo.dto.response.VnPayCreatePaymentResponse;
import com.example.demo.dto.response.ZaloPayCreatePaymentResponse;
import com.example.demo.service.MomoService;
import com.example.demo.service.PayPalService;
import com.example.demo.service.VNPayService;
import com.example.demo.service.ZaloPayService;
import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
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
    private final PayPalService payPalService;

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


    // Tạo thanh toán PayPal
    @PostMapping("/create-paypal-payment")
    public ResponseEntity<String> createPaypalPayment(@RequestParam Double total,
                                                      @RequestParam String currency,
                                                      @RequestParam String cancelUrl,
                                                      @RequestParam String successUrl)
    {
        try {
            com.paypal.api.payments.Payment payment = payPalService.createPayment(total, currency, "paypal", "sale",
                    "Payment description", cancelUrl, successUrl);

            // Chuyển hướng đến URL PayPal
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(link.getHref());
                }
            }
            return ResponseEntity.status(500).body("Error: PayPal approval URL not found.");
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating PayPal payment.");
        }
    }

    // Xử lý thông báo từ PayPal sau khi thanh toán
    @GetMapping("/paypal-return")
    public String paypalReturn(@RequestParam String paymentId, @RequestParam String payerId) {
        try {
            String payment = payPalService.executePayment(paymentId, payerId);
            return "Payment executed successfully: " + payment.toString();
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return "Error executing payment.";
        }
    }
}
