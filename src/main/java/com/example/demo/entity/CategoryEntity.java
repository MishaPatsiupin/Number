package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

}
