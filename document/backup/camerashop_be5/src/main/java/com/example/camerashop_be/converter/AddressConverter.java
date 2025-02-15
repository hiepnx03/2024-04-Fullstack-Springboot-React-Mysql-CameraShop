package com.example.camerashop_be.converter;

import com.example.camerashop_be.dto.AddressDTO;
import com.example.camerashop_be.entity.Address;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class AddressConverter {
	private final ModelMapper modelMapper;

	public Address convertToEntity(AddressDTO addressDTO) {
		return modelMapper.map(addressDTO, Address.class);
	}

	public AddressDTO convertToDTO(Address address) {
		return modelMapper.map(address, AddressDTO.class);
	}

	public Page<AddressDTO> convertToDTO(Page<Address> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToDTO);
	}
}
