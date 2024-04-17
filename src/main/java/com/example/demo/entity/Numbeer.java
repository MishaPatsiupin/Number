package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

/** The type Numbeer. */
@Data
@Entity
@Table(name = "number")
public class Numbeer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @Column(name = "number_data")
  private long numberData;
}
