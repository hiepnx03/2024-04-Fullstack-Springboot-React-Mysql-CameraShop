package com.example.demo.converter;

import com.example.demo.dto.AddressDTO;
import com.example.demo.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    private final ModelMapper modelMapper;

    public AddressConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AddressDTO convertToDTO(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }
    public Address convertToEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }
}
