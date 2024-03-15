package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fact_category")
public class FactCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "fact_id", referencedColumnName = "id")
    private FactEntity fact;

}