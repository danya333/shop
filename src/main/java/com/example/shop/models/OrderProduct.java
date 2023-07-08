package com.example.shop.models;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "orders_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private short number;

    @ManyToOne
    @JoinColumn(name = "orders_order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "products_product_id")
    private Product product;

}
