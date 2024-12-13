package com.example.demo.controller;

import com.example.demo.dto.AddressDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ResponseObject> addAddress(@RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO savedAddress = addressService.addAddress(addressDTO);
            return ResponseEntity.ok(new ResponseObject("200", "Address created successfully", savedAddress));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error creating address", null));
        }
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ResponseObject> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
            if (updatedAddress == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Address not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Address updated successfully", updatedAddress));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error updating address", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllAddresses() {
        try {
            List<AddressDTO> addresses = addressService.getAllAddressesByUserId(null); // Nếu không filter theo userId
            return ResponseEntity.ok(new ResponseObject("200", "Addresses retrieved successfully", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving addresses", null));
        }
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ResponseObject> getAddressById(@PathVariable Long addressId) {
        try {
            AddressDTO address = addressService.getAddressById(addressId);
            if (address == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "Address not found", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Address retrieved successfully", address));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving address", null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getAddressesByUserId(@PathVariable Long userId) {
        try {
            List<AddressDTO> addresses = addressService.getAllAddressesByUserId(userId);
            if (addresses.isEmpty()) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "No addresses found for user", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Addresses retrieved successfully", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving addresses", null));
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ResponseObject> deleteAddress(@PathVariable Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok(new ResponseObject("200", "Address deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error deleting address", null));
        }
    }

    @GetMapping("/user/{userId}/default")
    public ResponseEntity<ResponseObject> getDefaultAddressByUserId(@PathVariable Long userId) {
        try {
            AddressDTO defaultAddress = addressService.getDefaultAddressByUserId(userId);
            if (defaultAddress == null) {
                return ResponseEntity.status(404).body(new ResponseObject("404", "No default address found for user", null));
            }
            return ResponseEntity.ok(new ResponseObject("200", "Default address retrieved successfully", defaultAddress));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving default address", null));
        }
    }
}
