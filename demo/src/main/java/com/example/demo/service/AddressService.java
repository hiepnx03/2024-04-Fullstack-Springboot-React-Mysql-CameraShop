package com.example.demo.service;

import com.example.demo.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO addAddress(AddressDTO addressDTO);

    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);

    List<AddressDTO> getAllAddressesByUserId(Long userId);

    AddressDTO getAddressById(Long addressId);

    void deleteAddress(Long addressId);

    AddressDTO getDefaultAddressByUserId(Long userId);
}
