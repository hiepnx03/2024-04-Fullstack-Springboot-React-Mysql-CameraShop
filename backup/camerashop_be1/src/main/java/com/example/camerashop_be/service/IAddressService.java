package com.example.camerashop_be.service;

import com.example.camerashop_be.dto.AddressDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAddressService {
    Page<AddressDTO> getAllByUserId(Long price, Integer status, Integer page, Integer size);

    AddressDTO get(Long id);

    AddressDTO add(AddressDTO addressDTO);

    AddressDTO update(AddressDTO addressDTO);

    void delete(Long id);

    void delete(List<Long> ids);
}
