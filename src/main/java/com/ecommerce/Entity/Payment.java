package com.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders orders;

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

    private String razorpayRefundId;
    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
