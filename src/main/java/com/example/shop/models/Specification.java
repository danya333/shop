package com.example.shop.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "specifications")
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "specification")
    private List<Description> descriptions;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
