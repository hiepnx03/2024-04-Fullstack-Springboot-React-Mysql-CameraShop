package com.example.demo.converter;

import com.example.demo.dto.VoucherDTO;
import com.example.demo.entity.Voucher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VoucherConverter {
    private final ModelMapper modelMapper;

    public VoucherConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VoucherDTO convertToDto(Voucher voucher) {
        return modelMapper.map(voucher, VoucherDTO.class);
    }
    public Voucher convertToEntity(VoucherDTO voucherDTO) {
        return modelMapper.map(voucherDTO, Voucher.class);
    }
}
