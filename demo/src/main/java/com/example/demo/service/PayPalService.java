package com.example.demo.service;

import com.example.demo.config.PayPalConfig;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayPalService {

    private final PayPalConfig payPalConfig;

    @Autowired
    public PayPalService(PayPalConfig payPalConfig) {
        this.payPalConfig = payPalConfig;
    }

    public Payment createPayment(Double total, String currency, String method, String intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setTotal(String.format("%.2f", total));
        amount.setCurrency(currency);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toUpperCase());

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(List.of(transaction));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        // Gửi yêu cầu tạo Payment đến PayPal API
        APIContext apiContext = payPalConfig.apiContext();
        return payment.create(apiContext);
    }

    // Xử lý Payment sau khi thành công
    public String executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        APIContext apiContext = payPalConfig.apiContext();
        Payment executedPayment = payment.execute(apiContext, paymentExecution);

        return executedPayment.toJSON();
    }
}
