package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private Long id;
    private String code;
    private Long cost;
    private Date startDate;
    private Date endDate;
    private Integer times;
    private Integer status;
}
