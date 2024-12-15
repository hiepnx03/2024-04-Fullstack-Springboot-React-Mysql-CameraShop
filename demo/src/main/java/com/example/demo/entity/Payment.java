package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends Base<String>  implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private EStatusPayment status;

    @Column(name="payer")
    private String payer;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_method")
    private EPaymentMethod paymentMethod;

    @Column(name="email")
    private String email;

    @OneToOne(mappedBy = "payment")
    @JsonIgnore
    private Order bill;
}
