package com.example.demo.controller;

import com.example.demo.dto.AddressDTO;
import com.example.demo.entity.ResponseObject;
import com.example.demo.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Add a new address", description = "Create and save a new address for the user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address created successfully"),
            @ApiResponse(responseCode = "500", description = "Error creating address")
    })
    @PostMapping
    public ResponseEntity<ResponseObject> addAddress(@RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO savedAddress = addressService.addAddress(addressDTO);
            return ResponseEntity.ok(new ResponseObject("200", "Address created successfully", savedAddress));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error creating address", null));
        }
    }

    @Operation(summary = "Update an existing address", description = "Update the details of an address by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "500", description = "Error updating address")
    })
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

    @Operation(summary = "Get all addresses", description = "Retrieve a list of all addresses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Error retrieving addresses")
    })
    @GetMapping
    public ResponseEntity<ResponseObject> getAllAddresses() {
        try {
            List<AddressDTO> addresses = addressService.getAllAddressesByUserId(null);
            return ResponseEntity.ok(new ResponseObject("200", "Addresses retrieved successfully", addresses));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error retrieving addresses", null));
        }
    }

    @Operation(summary = "Get an address by ID", description = "Retrieve details of an address using its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "500", description = "Error retrieving address")
    })
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

    @Operation(summary = "Get addresses by user ID", description = "Retrieve all addresses associated with a specific user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No addresses found for user"),
            @ApiResponse(responseCode = "500", description = "Error retrieving addresses")
    })
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

    @Operation(summary = "Delete an address", description = "Remove an address by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Error deleting address")
    })
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ResponseObject> deleteAddress(@PathVariable Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok(new ResponseObject("200", "Address deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseObject("500", "Error deleting address", null));
        }
    }

    @Operation(summary = "Get the default address of a user", description = "Retrieve the default address for a given user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Default address retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No default address found for user"),
            @ApiResponse(responseCode = "500", description = "Error retrieving default address")
    })
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
