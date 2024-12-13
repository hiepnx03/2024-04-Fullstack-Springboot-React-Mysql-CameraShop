package com.example.demo.service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import com.example.demo.converter.AddressConverter;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressConverter addressConverter;
    private final UserRepository userRepository;

    @Override
    public AddressDTO addAddress(AddressDTO addressDTO) {
        Address address = addressConverter.convertToEntity(addressDTO);
        Address savedAddress = addressRepository.save(address);
        return addressConverter.convertToDTO(savedAddress);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(addressId).orElse(null);
        if (existingAddress == null) {
            return null;
        }

        // Tìm User mới từ userId trong DTO
        User user = userRepository.findById(addressDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));


        existingAddress.setFirstName(addressDTO.getFirstName());
        existingAddress.setLastName(addressDTO.getLastName());
        existingAddress.setPhone(addressDTO.getPhone());
        existingAddress.setIsDefault(addressDTO.getIsDefault());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setDistrict(addressDTO.getDistrict());
        existingAddress.setWard(addressDTO.getWard());
        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setDescription(addressDTO.getDescription());
        existingAddress.setStatus(addressDTO.getStatus());
        // Cập nhật lại User cho Address
        existingAddress.setUser(user);


        Address updatedAddress = addressRepository.save(existingAddress);
        return addressConverter.convertToDTO(updatedAddress);
    }

    @Override
    public List<AddressDTO> getAllAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(addressConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        return address != null ? addressConverter.convertToDTO(address) : null;
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public AddressDTO getDefaultAddressByUserId(Long userId) {
        Address address = addressRepository.findByUserIdAndIsDefault(userId, 1);  // 1 tương đương với địa chỉ mặc định
        return address != null ? addressConverter.convertToDTO(address) : null;
    }
}
