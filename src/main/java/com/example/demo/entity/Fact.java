package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "fact")
public class Fact {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "number_id", referencedColumnName = "id")
    private Numbeer number;

    @Basic
    @Column(name = "description")
    private String description;

}
