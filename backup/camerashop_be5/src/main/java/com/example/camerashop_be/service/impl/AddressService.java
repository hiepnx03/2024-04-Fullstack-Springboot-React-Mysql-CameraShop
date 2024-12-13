package com.example.camerashop_be.service.impl;


import com.example.camerashop_be.converter.AddressConverter;
import com.example.camerashop_be.dto.AddressDTO;
import com.example.camerashop_be.entity.Address;
import com.example.camerashop_be.entity.User;
import com.example.camerashop_be.repository.AddressRepo;
import com.example.camerashop_be.repository.UserRepo;
import com.example.camerashop_be.service.IAddressService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AddressService implements IAddressService {
    private final AddressConverter addressConverter;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    @Override
    public Page<AddressDTO> getAllByUserId(Long userId, Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return addressConverter.convertToDTO(addressRepo.findAllByUserIdAndStatus(userId, status, pageable));
    }

    @Override
    public AddressDTO get(Long id) {
        return addressConverter.convertToDTO(addressRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address " + id + " does not exist!")));
    }

    @Transactional
    @Override
    public AddressDTO add(AddressDTO addressDTO) {
        if (addressDTO.getIsDefault() == 1) {
            this.setDefaultOnlyOne();
        }
        return addressConverter.convertToDTO(addressRepo.save(addressConverter.convertToEntity(addressDTO)));
    }

    private void setDefaultOnlyOne() {
        List<Address> addresses = addressRepo.findAllByIsDefault(1);
        List<Address> addressesChanged = addresses.stream().peek(e -> e.setIsDefault(0)).collect(Collectors.toList());
        addressRepo.saveAll(addressesChanged);
    }

    @Transactional
    @Override
    public AddressDTO update(AddressDTO addressDTO) {
        this.setDefaultOnlyOne();
        Address address = addressRepo.findById(addressDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Address " + addressDTO.getId() + " does not exist!"));

        address.setIsDefault(addressDTO.getIsDefault());
        address.setStatus(addressDTO.getStatus());
        address.setCity(addressDTO.getCity());
        address.setWard(addressDTO.getWard());
        address.setDistrict(addressDTO.getDistrict());
        address.setFirstName(addressDTO.getFirstName());
        address.setLastName(addressDTO.getLastName());
        address.setDescription(addressDTO.getDescription());
        User user = userRepo.findById(addressDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User " + addressDTO.getUserId() + " does not exist!"));
        address.setUser(user);
        address.setStreet(addressDTO.getStreet());
        address.setPhone(addressDTO.getPhone());
        return addressConverter.convertToDTO(addressRepo.save(address));
    }

    @Override
    public void delete(Long id) {
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address " + id + " does not exist!"));
        if (address.getIsDefault() == 1) {
            throw new RuntimeException("Cannot delete default address!");
        }
        addressRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {

        addressRepo.deleteAllById(ids);
    }
}
