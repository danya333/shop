package com.example.shop.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "descriptions")
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "specification_id")
    private Specification specification;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
