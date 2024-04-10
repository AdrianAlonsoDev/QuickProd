package dev.adrianalonso.dekra.quickprod.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/*@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity*/
public class Product {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Long id;
    private String name;
    private String description;
    private Double price;
}
