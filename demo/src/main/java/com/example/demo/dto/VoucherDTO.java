package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {

    private Long id;

    @NotBlank(message = "Voucher code cannot be blank.")
    @Size(min = 5, max = 20, message = "Voucher code must be between 5 and 20 characters.")
    private String code;

    @NotNull(message = "Cost cannot be null.")
    @Min(value = 0, message = "Cost must be greater than or equal to 0.")
    private Long cost;

    @NotNull(message = "Start date cannot be null.")
    @PastOrPresent(message = "Start date must be in the past or present.")
    private Date startDate;

    @NotNull(message = "End date cannot be null.")
    @Future(message = "End date must be in the future.")
    private Date endDate;

    @NotNull(message = "Times cannot be null.")
    @Min(value = 1, message = "Times must be greater than or equal to 1.")
    private Integer times;

    @NotNull(message = "Status cannot be null.")
    private Integer status;
}
