package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

/** The type Category. */
@Entity
@Data
@Table(name = "category")
public class Category {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private long id;

  @Basic
  @Column(name = "name")
  private String name;
}
