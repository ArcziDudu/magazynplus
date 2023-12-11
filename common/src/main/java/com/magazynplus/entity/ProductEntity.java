package com.magazynplus.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "t_product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private String producer;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Boolean availability;
    private String productNumber;
    private String imageLink;

}
