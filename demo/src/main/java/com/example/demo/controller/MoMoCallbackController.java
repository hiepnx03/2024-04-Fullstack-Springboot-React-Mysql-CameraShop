package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api")
@RestController
@Slf4j
public class MoMoCallbackController {

    private static final String SECRET_KEY = "your_momo_secret_key"; // Replace with your MoMo secret key

    @PostMapping("/momo/callback")
    public void handleMoMoCallback(@RequestBody Map<String, String> response) {
        log.info("Received MoMo callback: {}", response);

        try {
            // Extracting parameters from the callback response
            String orderId = response.get("orderId");
            String status = response.get("status");
            String signature = response.get("signature");

            // Validate the signature to ensure data integrity
            if (!isSignatureValid(response, signature)) {
                log.error("Invalid signature for orderId: {}", orderId);
                return;
            }

            // Handling successful payment
            if ("SUCCESS".equals(status)) {
                log.info("Payment successful for orderId: {}", orderId);
                // Update the order status in your system (e.g., mark as paid)
            } else {
                log.warn("Payment failed for orderId: {}", orderId);
                // Handle the failed payment (e.g., mark as failed)
            }
        } catch (Exception e) {
            log.error("Error processing MoMo callback", e);
        }
    }

    /**
     * Method to validate MoMo's signature (this is a basic example, replace with actual MoMo signature validation logic)
     */
    private boolean isSignatureValid(Map<String, String> response, String signature) {
        // You need to validate the signature using the MoMo signature validation logic
        // For example, concatenate the parameters and compare the signature with a hashed string
        StringBuilder dataToHash = new StringBuilder();
        response.forEach((key, value) -> {
            if (!"signature".equals(key)) { // Don't include the signature in the hash
                dataToHash.append(key).append("=").append(value).append("&");
            }
        });
        // Removing the last '&' character
        dataToHash.deleteCharAt(dataToHash.length() - 1);

        String computedSignature = computeSignature(dataToHash.toString(), SECRET_KEY);
        return computedSignature.equals(signature);
    }

    /**
     * Method to compute the signature (replace with MoMo's actual signature computation method)
     */
    private String computeSignature(String data, String secretKey) {
        // Here you would implement the actual signature hashing process using MoMo's specification
        // Typically this would be a HMAC SHA256 or similar encryption method
        return data + secretKey; // Simplified for the example
    }
}
