package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "fact_category")
public class FactCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fact_id", referencedColumnName = "id")
    private Fact fact;

    @Column(name = "author", nullable = false, columnDefinition = "text default 'UNKNOWN'")
    private String author;
}