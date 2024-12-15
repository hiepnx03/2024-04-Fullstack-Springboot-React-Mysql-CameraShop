package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "voucher")
@AllArgsConstructor
@NoArgsConstructor
public class Voucher  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_code", nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false)
    @Min(value = 0, message = "Cost must be greater than or equal to 0.")
    private Long cost;

    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date cannot be null.")
    @PastOrPresent(message = "Start date must be in the past or present.")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date cannot be null.")
    @Future(message = "End date must be in the future.")
    private Date endDate;

    @Column(nullable = false)
    @NotNull(message = "Times cannot be null.")
    @Min(value = 1, message = "Times must be greater than or equal to 1.")
    private Integer times;

    @Column(nullable = false)
    @NotNull(message = "Status cannot be null.")
    private Integer status;
}

