package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "fact")
public class FactEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "number_id", referencedColumnName = "id")
    private NumberEntity number;

    @Basic
    @Column(name = "description")
    private String description;

}
