package com.example.demo.controller;

import com.example.demo.dto.response.MomoCreatePaymentResponse;
import com.example.demo.dto.response.ZaloPayCreatePaymentResponse;
import com.example.demo.service.MoMoService;
import com.example.demo.service.PayPalService;
import com.example.demo.service.VNPayService;
import com.example.demo.service.ZaloPayService;
import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
@Slf4j
public class PaymentController {

    private final VNPayService vnPayService;
    private final ZaloPayService zaloPayService;
    private final MoMoService momoService;
    private final PayPalService payPalService;

    @Operation(summary = "Create VNPay Payment URL", description = "Generates a VNPay payment URL based on the provided amount.")
    @ApiResponse(responseCode = "200", description = "Payment URL generated successfully")
    @ApiResponse(responseCode = "500", description = "Error generating payment URL")
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(HttpServletRequest request, @RequestParam("amount") long amount) {
        try {
            String paymentUrl = vnPayService.createPaymentUrl(request, amount);
            return ResponseEntity.status(HttpStatus.OK).body(paymentUrl);
        } catch (UnsupportedEncodingException e) {
            log.error("Error generating payment URL", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating payment URL");
        }
    }

    @Operation(summary = "Create ZaloPay Payment URL", description = "Generates a ZaloPay payment URL.")
    @PostMapping("/create-zalopay-payment-url")
    public String generateZaloPayPaymentUrl(@RequestParam String orderId,
                                            @RequestParam Long amount,
                                            @RequestParam String orderInfo) {
        ZaloPayCreatePaymentResponse response = zaloPayService.createPayment(orderId, amount, orderInfo);
        return "Redirecting to ZaloPay: " + response.getOrderUrl();
    }

    @Operation(summary = "Create MoMo Payment URL", description = "Generates a MoMo payment URL.")
    @PostMapping("/create-momo-payment-url")
    public String createMomoPaymentUrl(@RequestParam String orderId,
                                       @RequestParam Long amount,
                                       @RequestParam String orderInfo) {
        MomoCreatePaymentResponse response = momoService.generateMomoPayPaymentUrl(orderId, amount, orderInfo);
        return "Redirecting to MoMo: " + response.getPayUrl();
    }

    @Operation(summary = "Create PayPal Payment", description = "Generates a PayPal payment link.")
    @PostMapping("/create-paypal-payment")
    public ResponseEntity<String> createPaypalPayment(@RequestParam Double total,
                                                      @RequestParam String currency,
                                                      @RequestParam String cancelUrl,
                                                      @RequestParam String successUrl) {
        try {
            com.paypal.api.payments.Payment payment = payPalService.createPayment(total, currency, "paypal", "sale",
                    "Payment description", cancelUrl, successUrl);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(link.getHref());
                }
            }
            return ResponseEntity.status(500).body("Error: PayPal approval URL not found.");
        } catch (PayPalRESTException e) {
            log.error("Error creating PayPal payment", e);
            return ResponseEntity.status(500).body("Error creating PayPal payment.");
        }
    }

    @Operation(summary = "Handle PayPal Return", description = "Handles the return response from PayPal after payment.")
    @GetMapping("/paypal-return")
    public String paypalReturn(@RequestParam String paymentId, @RequestParam String payerId) {
        try {
            String payment = payPalService.executePayment(paymentId, payerId);
            return "Payment executed successfully: " + payment;
        } catch (PayPalRESTException e) {
            log.error("Error executing PayPal payment", e);
            return "Error executing payment.";
        }
    }
}
