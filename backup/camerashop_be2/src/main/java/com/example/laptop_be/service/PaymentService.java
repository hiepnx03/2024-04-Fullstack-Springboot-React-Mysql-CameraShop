package com.example.laptop_be.service;
import com.example.laptop_be.converter.Converter;
import com.example.laptop_be.repository.PaymentRepository;
import com.example.laptop_be.dto.PaymentDTO;
import com.example.laptop_be.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private Converter converter;

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = converter.convertToEntity(paymentDTO);
        Payment savedPayment = paymentRepository.save(payment);
        return converter.convertToDto(savedPayment);
    }

    public PaymentDTO getPaymentById(int id) {
        Payment payment = paymentRepository.findById(id).orElse(null);
        return converter.convertToDto(payment);
    }

    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    public PaymentDTO updatePayment(int id, PaymentDTO paymentDTO) {
        Payment payment = converter.convertToEntity(paymentDTO);
        payment.setIdPayment(id);
        Payment updatedPayment = paymentRepository.save(payment);
        return converter.convertToDto(updatedPayment);
    }

    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }
}
