package com.example.demo.service;

import com.example.demo.dto.VoucherDTO;
import com.example.demo.dto.request.ApplyVoucherRequest;
import com.example.demo.dto.response.ApplyVoucherResponse;

import java.util.List;

public interface VoucherService {
    VoucherDTO addVoucher(VoucherDTO voucherDTO);
    VoucherDTO updateVoucher(Long voucherId, VoucherDTO voucherDTO);
    List<VoucherDTO> getAllVouchers();
    VoucherDTO getVoucherById(Long voucherId);
    void deleteVoucher(Long voucherId);
    VoucherDTO findVoucherByCode(String code);

    ApplyVoucherResponse applyVoucher(ApplyVoucherRequest request);

}
