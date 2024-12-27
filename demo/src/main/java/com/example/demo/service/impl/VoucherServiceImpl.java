package com.example.demo.service.impl;

import com.example.demo.dto.VoucherDTO;
import com.example.demo.dto.request.ApplyVoucherRequest;
import com.example.demo.dto.response.ApplyVoucherResponse;
import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.converter.VoucherConverter;
import com.example.demo.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherConverter voucherConverter;

    @Override
    public VoucherDTO addVoucher(VoucherDTO voucherDTO) {
        // Chuyển đổi từ DTO sang Entity và lưu vào cơ sở dữ liệu
        Voucher voucher = voucherConverter.convertToEntity(voucherDTO);
        Voucher savedVoucher = voucherRepository.save(voucher);
        return voucherConverter.convertToDto(savedVoucher);
    }

    @Override
    public VoucherDTO updateVoucher(Long voucherId, VoucherDTO voucherDTO) {
        // Kiểm tra xem voucher có tồn tại không
        Optional<Voucher> existingVoucherOpt = voucherRepository.findById(voucherId);
        if (!existingVoucherOpt.isPresent()) {
            return null; // Hoặc ném exception nếu cần
        }

        // Cập nhật các trường của voucher
        Voucher existingVoucher = existingVoucherOpt.get();
        existingVoucher.setCode(voucherDTO.getCode());
        existingVoucher.setCost(voucherDTO.getCost());
        existingVoucher.setStartDate(voucherDTO.getStartDate());
        existingVoucher.setEndDate(voucherDTO.getEndDate());
        existingVoucher.setTimes(voucherDTO.getTimes());
        existingVoucher.setStatus(voucherDTO.getStatus());

        // Lưu lại voucher đã cập nhật
        Voucher updatedVoucher = voucherRepository.save(existingVoucher);
        return voucherConverter.convertToDto(updatedVoucher);
    }

    @Override
    public List<VoucherDTO> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(voucherConverter::convertToDto) // Chuyển từ Entity sang DTO
                .collect(Collectors.toList());
    }

    @Override
    public VoucherDTO getVoucherById(Long voucherId) {
        Optional<Voucher> voucher = voucherRepository.findById(voucherId);
        return voucher.map(voucherConverter::convertToDto).orElse(null);
    }

    @Override
    public void deleteVoucher(Long voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Override
    public VoucherDTO findVoucherByCode(String code) {
        Optional<Voucher> voucher = voucherRepository.findByCode(code);
        return voucher.map(voucherConverter::convertToDto).orElse(null);
    }

    @Override
    public ApplyVoucherResponse applyVoucher(ApplyVoucherRequest request) {
        // Tìm voucher theo mã
        Optional<Voucher> voucherOpt = voucherRepository.findByCode(request.getVoucherCode());

        // Kiểm tra voucher có tồn tại không
        if (!voucherOpt.isPresent()) {
            return new ApplyVoucherResponse(false, "Voucher không tồn tại", null, request.getOrderTotal());
        }

        Voucher voucher = voucherOpt.get();

        // Kiểm tra trạng thái của voucher (ví dụ: có hết hạn hay không)
        if (voucher.getStatus() != 1) {
            return new ApplyVoucherResponse(false, "Voucher không hợp lệ hoặc đã hết hạn", null, request.getOrderTotal());
        }

        // Kiểm tra số lần còn lại của voucher
        if (voucher.getTimes() <= 0) {
            return new ApplyVoucherResponse(false, "Voucher đã được sử dụng hết", null, request.getOrderTotal());
        }

        // Kiểm tra ngày hiệu lực của voucher
        if (voucher.getStartDate().after(new java.util.Date()) || voucher.getEndDate().before(new java.util.Date())) {
            return new ApplyVoucherResponse(false, "Voucher đã hết hiệu lực", null, request.getOrderTotal());
        }

        // Giả sử voucher là một phần trăm chiết khấu
        long discountAmount = request.getOrderTotal() * 10 / 100; // Giảm 10% (Ví dụ)
        long finalAmount = request.getOrderTotal() - discountAmount;

        // Cập nhật số lần sử dụng voucher
        voucher.setTimes(voucher.getTimes() - 1);
        voucherRepository.save(voucher);

        return new ApplyVoucherResponse(true, "Voucher áp dụng thành công", discountAmount, finalAmount);
    }
}
