package dev.adrianalonso.dekra.quickprod.fraud.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_fraud_customer_check")
public class FraudCustomerCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerKey;
    private String idType;
    private String idValue;
    private LocalDateTime created;
}
